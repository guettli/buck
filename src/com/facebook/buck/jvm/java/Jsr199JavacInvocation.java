/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.buck.jvm.java;

import static com.facebook.buck.jvm.java.abi.AbiGenerationModeUtils.checkForSourceOnlyAbiCompatibility;
import static com.facebook.buck.jvm.java.abi.AbiGenerationModeUtils.getDiagnosticKindForSourceOnlyAbiCompatibility;
import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

import com.facebook.buck.core.exceptions.BuckUncheckedExecutionException;
import com.facebook.buck.core.exceptions.HumanReadableException;
import com.facebook.buck.core.filesystems.AbsPath;
import com.facebook.buck.core.filesystems.RelPath;
import com.facebook.buck.core.util.log.Logger;
import com.facebook.buck.event.api.BuckTracing;
import com.facebook.buck.io.filesystem.impl.ProjectFilesystemUtils;
import com.facebook.buck.javacd.model.AbiGenerationMode;
import com.facebook.buck.javacd.model.ResolvedJavacOptions.JavacPluginJsr199Fields;
import com.facebook.buck.jvm.core.BuildTargetValue;
import com.facebook.buck.jvm.java.abi.SourceBasedAbiStubber;
import com.facebook.buck.jvm.java.abi.SourceVersionUtils;
import com.facebook.buck.jvm.java.abi.StubGenerator;
import com.facebook.buck.jvm.java.abi.source.api.FrontendOnlyJavacTaskProxy;
import com.facebook.buck.jvm.java.abi.source.api.SourceOnlyAbiRuleInfoFactory;
import com.facebook.buck.jvm.java.abi.source.api.StopCompilation;
import com.facebook.buck.jvm.java.plugin.PluginLoader;
import com.facebook.buck.jvm.java.plugin.api.BuckJavacTaskListener;
import com.facebook.buck.jvm.java.plugin.api.BuckJavacTaskProxy;
import com.facebook.buck.jvm.java.plugin.api.PluginClassLoader;
import com.facebook.buck.jvm.java.plugin.api.PluginClassLoaderFactory;
import com.facebook.buck.jvm.java.tracing.JavacPhaseEventLogger;
import com.facebook.buck.jvm.java.tracing.TracingTaskListener;
import com.facebook.buck.jvm.java.tracing.TranslatingJavacPhaseTracer;
import com.facebook.buck.util.concurrent.MostExecutors.NamedThreadFactory;
import com.facebook.buck.util.zip.JarBuilder;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

/** Jsr199Javac invocation object created during java compilation. */
class Jsr199JavacInvocation implements ResolvedJavac.Invocation {

  private static final Logger LOG = Logger.get(Jsr199JavacInvocation.class);
  private static final ListeningExecutorService THREAD_POOL =
      MoreExecutors.listeningDecorator(
          Executors.newCachedThreadPool(new NamedThreadFactory("javac")));

  static final String NO_JAVA_FILES_ERROR_MESSAGE =
      "No Java files provided for library compilation";

  private final Supplier<JavaCompiler> compilerConstructor;
  private final JavacExecutionContext context;
  private final BuildTargetValue invokingRule;
  private final CompilerOutputPathsValue compilerOutputPathsValue;
  private final AbiGenerationMode abiCompatibilityMode;
  private final ImmutableList<String> options;
  private final ImmutableList<JavacPluginJsr199Fields> annotationProcessors;
  private final ImmutableList<JavacPluginJsr199Fields> javacPlugins;
  private final ImmutableSortedSet<RelPath> javaSourceFilePaths;
  private final RelPath pathToSrcsList;
  private final AbiGenerationMode abiGenerationMode;
  @Nullable private final JarParameters abiJarParameters;
  @Nullable private final JarParameters libraryJarParameters;
  @Nullable private final SourceOnlyAbiRuleInfoFactory ruleInfoFactory;
  private final boolean trackClassUsage;
  private final boolean trackJavacPhaseEvents;

  @Nullable private CompilerWorker worker;

  public Jsr199JavacInvocation(
      Supplier<JavaCompiler> compilerConstructor,
      JavacExecutionContext context,
      BuildTargetValue invokingRule,
      CompilerOutputPathsValue compilerOutputPathsValue,
      ImmutableList<String> options,
      ImmutableList<JavacPluginJsr199Fields> annotationProcessors,
      ImmutableList<JavacPluginJsr199Fields> javacPlugins,
      ImmutableSortedSet<RelPath> javaSourceFilePaths,
      RelPath pathToSrcsList,
      boolean trackClassUsage,
      boolean trackJavacPhaseEvents,
      @Nullable JarParameters abiJarParameters,
      @Nullable JarParameters libraryJarParameters,
      AbiGenerationMode abiGenerationMode,
      AbiGenerationMode abiCompatibilityMode,
      @Nullable SourceOnlyAbiRuleInfoFactory ruleInfoFactory) {
    this.compilerConstructor = compilerConstructor;
    this.context = context;
    this.invokingRule = invokingRule;
    this.compilerOutputPathsValue = compilerOutputPathsValue;
    this.abiCompatibilityMode = abiCompatibilityMode;
    this.options = options;
    this.annotationProcessors = annotationProcessors;
    this.javacPlugins = javacPlugins;
    this.javaSourceFilePaths = javaSourceFilePaths;
    this.pathToSrcsList = pathToSrcsList;
    this.trackClassUsage = trackClassUsage;
    this.trackJavacPhaseEvents = trackJavacPhaseEvents;
    this.abiJarParameters = abiJarParameters;
    this.libraryJarParameters = libraryJarParameters;
    this.abiGenerationMode = abiGenerationMode;
    this.ruleInfoFactory = ruleInfoFactory;
  }

  @Override
  public int buildSourceOnlyAbiJar() throws InterruptedException {
    return getWorker()
        .buildSourceOnlyAbiJar(
            compilerOutputPathsValue.getSourceOnlyAbiCompilerOutputPath().getOutputJarDirPath());
  }

  @Override
  public int buildSourceAbiJar() throws InterruptedException {
    return getWorker()
        .buildSourceAbiJar(
            compilerOutputPathsValue.getSourceAbiCompilerOutputPath().getOutputJarDirPath());
  }

  @Override
  public int buildClasses() throws InterruptedException {
    // write javaSourceFilePaths to classes file
    // for buck user to have a list of all .java files to be compiled
    // since we do not print them out to console in case of error
    try {
      AbsPath ruleCellRoot = context.getRuleCellRoot();

      ProjectFilesystemUtils.writeLinesToPath(
          ruleCellRoot,
          () ->
              javaSourceFilePaths.stream()
                  .map(Object::toString)
                  .map(ResolvedJavac.ARGFILES_ESCAPER::apply)
                  .iterator(),
          pathToSrcsList.getPath());
    } catch (IOException e) {
      context
          .getEventSink()
          .reportThrowable(
              e,
              "Cannot write list of .java files to compile to %s file! Terminating compilation.",
              pathToSrcsList);
      return 1;
    }

    return getWorker().buildClasses();
  }

  private CompilerWorker getWorker() {
    if (worker == null) {
      worker = new CompilerWorker(THREAD_POOL);
    }

    return worker;
  }

  @Override
  public void close() {
    // Must close the worker first so that the compiler has a chance to exit.
    if (worker != null) {
      worker.close();
      worker = null;
    }
  }

  private class CompilerWorker implements AutoCloseable {

    private final ListeningExecutorService executor;

    private final SettableFuture<Integer> compilerResult = SettableFuture.create();
    private final SettableFuture<Boolean> shouldCompileFullJar = SettableFuture.create();
    private final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    private final List<AutoCloseable> closeables = new ArrayList<>();

    @Nullable private BuckJavacTaskProxy lazyJavacTask;

    /** A perf event that's used to show, on the background thread, what rule is being built. */
    @Nullable private JavacEventSinkScopedSimplePerfEvent targetEvent;

    @Nullable private String compilerThreadName;
    @Nullable private JavacPhaseEventLogger phaseEventLogger;
    @Nullable private JavaInMemoryFileManager inMemoryFileManager;
    @Nullable private final ClassUsageTracker classUsageTracker;
    @Nullable private Jsr199TracingBridge tracingBridge;

    private CompilerWorker(ListeningExecutorService executor) {
      this.executor = executor;

      classUsageTracker = trackClassUsage ? new ClassUsageTracker() : null;
    }

    public int buildSourceOnlyAbiJar(RelPath outputJarDirPath) throws InterruptedException {
      return buildAbiJar(true, outputJarDirPath);
    }

    public int buildSourceAbiJar(RelPath outputJarDirPath) throws InterruptedException {
      return buildAbiJar(false, outputJarDirPath);
    }

    private int buildAbiJar(boolean buildSourceOnlyAbi, RelPath outputJarDirPath)
        throws InterruptedException {
      SettableFuture<Integer> abiResult = SettableFuture.create();
      Futures.addCallback(
          compilerResult,
          new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
              abiResult.set(result);
            }

            @Override
            public void onFailure(Throwable t) {
              // Propagate failure
              abiResult.setException(t);
            }
          },
          directExecutor());

      JarParameters jarParameters = Objects.requireNonNull(abiJarParameters);
      BuckJavacTaskProxy javacTask = getJavacTask(buildSourceOnlyAbi);
      javacTask.addPostEnterCallback(
          topLevelTypes -> {
            try {
              if (buildSuccessful()) {
                // Only attempt to build stubs if the build is successful so far; errors can
                // put javac into an unknown state.
                JarBuilder jarBuilder = newJarBuilder(jarParameters).setShouldHashEntries(true);
                StubGenerator stubGenerator =
                    new StubGenerator(
                        getTargetVersion(options),
                        javacTask.getElements(),
                        javacTask.getTypes(),
                        javacTask.getMessager(),
                        jarBuilder,
                        context.getEventSink(),
                        abiCompatibilityMode,
                        options.contains("-parameters"));
                stubGenerator.generate(topLevelTypes);
                jarBuilder.createJarFile(
                    ProjectFilesystemUtils.getPathForRelativePath(
                        context.getRuleCellRoot(), jarParameters.getJarPath()));
              }

              debugLogDiagnostics();
              if (buildSuccessful()) {
                if (classUsageTracker != null) {
                  new DefaultClassUsageFileWriter()
                      .writeFile(
                          classUsageTracker,
                          CompilerOutputPaths.getDepFilePath(outputJarDirPath),
                          context.getRuleCellRoot(),
                          context.getConfiguredBuckOut(),
                          context.getCellToPathMappings());
                }
                abiResult.set(0);
              } else {
                reportDiagnosticsToUser();
                abiResult.set(1);
              }

              if (invokingRule.isSourceAbi()) {
                switchToFullJarIfRequested();
              }
            } catch (IOException e) {
              abiResult.setException(e);
            } catch (InterruptedException | ExecutionException e) {
              // These come from the get on shouldCompileFullJar, which should never throw
              throw new AssertionError(e);
            }
          });

      try {
        String threadName = startCompiler(javacTask);
        try (JavacEventSinkScopedSimplePerfEvent ignore =
            new JavacEventSinkScopedSimplePerfEvent(context.getEventSink(), threadName)) {
          return abiResult.get();
        }
      } catch (ExecutionException e) {
        Throwables.throwIfUnchecked(e.getCause());
        throw new HumanReadableException("Failed to generate abi: %s", e.getCause().getMessage());
      }
    }

    private void switchToFullJarIfRequested() throws InterruptedException, ExecutionException {
      Objects.requireNonNull(targetEvent).close();

      // Make a new event to capture the time spent waiting for the next stage in the
      // pipeline (or for the pipeline to realize it's done)
      targetEvent =
          new JavacEventSinkScopedSimplePerfEvent(context.getEventSink(), "Waiting for pipeline");
      if (!shouldCompileFullJar.get()) {
        // targetEvent will be closed in startCompiler
        throw new StopCompilation();
      }
      targetEvent.close();

      // Now start tracking the full jar
      String libraryTargetFullyQualifiedName =
          compilerOutputPathsValue.getLibraryTargetFullyQualifiedName();
      Objects.requireNonNull(tracingBridge).setBuildTargetName(libraryTargetFullyQualifiedName);
      Objects.requireNonNull(phaseEventLogger)
          .setBuildTargetFullyQualifiedName(libraryTargetFullyQualifiedName);
      targetEvent =
          new JavacEventSinkScopedSimplePerfEvent(
              context.getEventSink(), libraryTargetFullyQualifiedName);
    }

    public int buildClasses() throws InterruptedException {
      shouldCompileFullJar.set(true);
      try {
        String threadName = startCompiler(getJavacTask(false));
        try (JavacEventSinkScopedSimplePerfEvent ignore =
            new JavacEventSinkScopedSimplePerfEvent(context.getEventSink(), threadName)) {
          return compilerResult.get();
        }
      } catch (ExecutionException e) {
        Throwables.throwIfUnchecked(e.getCause());
        throw new HumanReadableException("Failed to compile: %s", e.getCause().getMessage());
      }
    }

    private void debugLogDiagnostics() {
      for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
        LOG.debug("javac: %s", DiagnosticPrettyPrinter.format(diagnostic));
      }
    }

    private void reportDiagnosticsToUser() {
      if (context.getVerbosity().shouldPrintStandardInformation()) {
        List<Diagnostic<? extends JavaFileObject>> cleanDiagnostics =
            DiagnosticCleaner.clean(diagnostics.getDiagnostics());

        int numErrors = 0;
        int numWarnings = 0;
        for (Diagnostic<? extends JavaFileObject> diagnostic : cleanDiagnostics) {
          Diagnostic.Kind kind = diagnostic.getKind();
          if (kind == Diagnostic.Kind.ERROR) {
            ++numErrors;
          } else if (kind == Diagnostic.Kind.WARNING || kind == Diagnostic.Kind.MANDATORY_WARNING) {
            ++numWarnings;
          }

          context.getStdErr().println(DiagnosticPrettyPrinter.format(diagnostic));
        }

        if (numErrors > 0 || numWarnings > 0) {
          context.getStdErr().printf("Errors: %d. Warnings: %d.\n", numErrors, numWarnings);
        }
      }
    }

    private boolean buildSuccessful() {
      return diagnostics.getDiagnostics().stream()
          .noneMatch(diag -> diag.getKind() == Diagnostic.Kind.ERROR);
    }

    private void addCloseable(Object maybeCloseable) {
      if (maybeCloseable instanceof AutoCloseable) {
        closeables.add((AutoCloseable) maybeCloseable);
      }
    }

    @Override
    public void close() {
      if (!compilerResult.isDone()) {
        // Make sure `.get()` won't hang forever if `compilerResult` is not initialized.
        // Note, `set` is no-op is `.setFuture` is already called.
        compilerResult.set(1);

        shouldCompileFullJar.set(false);
        try {
          compilerResult.get();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
          throw new AssertionError(e);
        }
      }
    }

    private String startCompiler(BuckJavacTaskProxy javacTask)
        throws ExecutionException, InterruptedException {
      if (compilerThreadName == null) {
        SettableFuture<String> threadName = SettableFuture.create();
        compilerResult.setFuture(
            executor.submit(
                () -> {
                  threadName.set(Thread.currentThread().getName());
                  tracingBridge =
                      new Jsr199TracingBridge(
                          context.getEventSink(), invokingRule.getFullyQualifiedName());
                  BuckTracing.setCurrentThreadTracingInterface(tracingBridge);
                  targetEvent =
                      new JavacEventSinkScopedSimplePerfEvent(
                          context.getEventSink(), invokingRule.getFullyQualifiedName());
                  try {
                    boolean success = false;
                    try {
                      success = javacTask.call();
                    } catch (IllegalStateException ex) {
                      if (ex.getLocalizedMessage().equals("no source files")
                          || ex.getLocalizedMessage().equals("error: no source files")) {
                        success = true;
                      }
                    }
                    if (javacTask instanceof FrontendOnlyJavacTaskProxy) {
                      if (success) {
                        return 0;
                      } else {
                        debugLogDiagnostics();
                        reportDiagnosticsToUser();
                        return 1;
                      }
                    }

                    debugLogDiagnostics();

                    if (success && buildSuccessful()) {
                      if (classUsageTracker != null) {
                        new DefaultClassUsageFileWriter()
                            .writeFile(
                                classUsageTracker,
                                CompilerOutputPaths.getDepFilePath(
                                    compilerOutputPathsValue
                                        .getLibraryCompilerOutputPath()
                                        .getOutputJarDirPath()),
                                context.getRuleCellRoot(),
                                context.getConfiguredBuckOut(),
                                context.getCellToPathMappings());
                      }
                    } else {
                      reportDiagnosticsToUser();
                      return 1;
                    }

                    if (libraryJarParameters == null) {
                      return 0;
                    }

                    return newJarBuilder(libraryJarParameters)
                        .createJarFile(
                            Objects.requireNonNull(
                                ProjectFilesystemUtils.getPathForRelativePath(
                                    context.getRuleCellRoot(), libraryJarParameters.getJarPath())));
                  } catch (RuntimeException e) {
                    if (e.getCause() instanceof StopCompilation) {
                      return 0;
                    } else if (javacTask instanceof FrontendOnlyJavacTaskProxy) {
                      throw new HumanReadableException(
                          e,
                          "The compiler crashed attempting to generate a source-only ABI: %s.\n"
                              + "Try building %s instead and fixing any errors that are emitted.\n"
                              + "If there are none, file an issue, with the crash trace from the log.\n",
                          invokingRule.getFullyQualifiedName(),
                          compilerOutputPathsValue.getLibraryTargetFullyQualifiedName());
                    } else {
                      throw new BuckUncheckedExecutionException(
                          e.getCause() != null ? e.getCause() : e, "When running javac");
                    }
                  } finally {
                    for (AutoCloseable closeable : Lists.reverse(closeables)) {
                      try {
                        closeable.close();
                      } catch (Exception e) {
                        LOG.warn(e, "Unable to close %s; we may be leaking memory.", closeable);
                      }
                    }
                    targetEvent.close();
                  }
                }));

        compilerThreadName = threadName.get();
      }

      return compilerThreadName;
    }

    private BuckJavacTaskProxy getJavacTask(boolean generatingSourceOnlyAbi) {
      if (lazyJavacTask == null) {
        try {
          JavaCompiler compiler = compilerConstructor.get();

          StandardJavaFileManager standardFileManager =
              compiler.getStandardFileManager(null, null, null);
          addCloseable(standardFileManager);

          // Ensure plugins are loaded from their own classloader.
          PluginFactory pluginFactory =
              new PluginFactory(
                  compiler.getClass().getClassLoader(), context.getClassLoaderCache());

          PluginLoaderJavaFileManager fileManager;
          if (libraryJarParameters != null) {
            Path directToJarPath =
                ProjectFilesystemUtils.getPathForRelativePath(
                    context.getRuleCellRoot(), libraryJarParameters.getJarPath());
            inMemoryFileManager =
                new JavaInMemoryFileManager(
                    standardFileManager,
                    directToJarPath,
                    libraryJarParameters.getRemoveEntryPredicate());
            addCloseable(inMemoryFileManager);
            fileManager =
                new PluginLoaderJavaFileManager(inMemoryFileManager, pluginFactory, javacPlugins);
          } else {
            inMemoryFileManager = null;
            fileManager =
                new PluginLoaderJavaFileManager(standardFileManager, pluginFactory, javacPlugins);
          }

          Iterable<? extends JavaFileObject> compilationUnits;
          try {
            compilationUnits =
                createCompilationUnits(
                    fileManager, context.getRuleCellRoot()::resolve, javaSourceFilePaths);
            compilationUnits.forEach(this::addCloseable);
          } catch (IOException e) {
            LOG.warn(e, "Error building compilation units");
            throw e;
          }

          List<String> classNamesForAnnotationProcessing = ImmutableList.of();
          Writer compilerOutputWriter =
              new java.io.PrintWriter(context.getStdErr()); // NOPMD required by API
          PluginClassLoaderFactory loaderFactory =
              PluginLoader.newFactory(context.getClassLoaderCache());

          ListenableFileManager wrappedFileManager = new ListenableFileManager(fileManager);
          if (classUsageTracker != null) {
            wrappedFileManager.addListener(classUsageTracker);
          }
          BuckJavacTaskProxy javacTask;
          if (generatingSourceOnlyAbi) {
            javacTask =
                FrontendOnlyJavacTaskProxy.getTask(
                    loaderFactory,
                    compiler,
                    compilerOutputWriter,
                    wrappedFileManager,
                    diagnostics,
                    options,
                    classNamesForAnnotationProcessing,
                    compilationUnits);
          } else {
            javacTask =
                BuckJavacTaskProxy.getTask(
                    loaderFactory,
                    compiler,
                    compilerOutputWriter,
                    wrappedFileManager,
                    diagnostics,
                    options,
                    classNamesForAnnotationProcessing,
                    compilationUnits);
          }

          PluginClassLoader pluginLoader = loaderFactory.getPluginClassLoader(javacTask);

          BuckJavacTaskListener taskListener = null;
          if (checkForSourceOnlyAbiCompatibility(abiGenerationMode)
              && !generatingSourceOnlyAbi
              && ruleInfoFactory != null) {
            taskListener =
                SourceBasedAbiStubber.newValidatingTaskListener(
                    pluginLoader,
                    javacTask,
                    ruleInfoFactory.create(fileManager),
                    () ->
                        diagnostics.getDiagnostics().stream()
                            .anyMatch(diagnostic -> diagnostic.getKind() == Diagnostic.Kind.ERROR),
                    getDiagnosticKindForSourceOnlyAbiCompatibility(abiGenerationMode));
          }

          phaseEventLogger =
              new JavacPhaseEventLogger(
                  invokingRule.getFullyQualifiedName(), context.getEventSink());
          TranslatingJavacPhaseTracer tracer = new TranslatingJavacPhaseTracer(phaseEventLogger);
          // TranslatingJavacPhaseTracer is AutoCloseable so that it can detect the end of tracing
          // in some unusual situations
          addCloseable(tracer);
          if (trackJavacPhaseEvents) {
            javacTask.setTaskListener(new TracingTaskListener(tracer, taskListener));
          }

          // Ensure annotation processors are loaded from their own classloader. If we don't do
          // this, then the evidence suggests that they get one polluted with Buck's own classpath,
          // which means that libraries that have dependencies on different versions of Buck's deps
          // may choke with novel errors that don't occur on the command line.
          AnnotationProcessorFactory processorFactory =
              new AnnotationProcessorFactory(
                  context.getEventSink(),
                  compiler.getClass().getClassLoader(),
                  context.getClassLoaderCache(),
                  invokingRule.getFullyQualifiedName());
          addCloseable(processorFactory);

          javacTask.setProcessors(processorFactory.createProcessors(annotationProcessors));
          lazyJavacTask = javacTask;
        } catch (IOException e) {
          LOG.error(e);
          throw new HumanReadableException("IOException during compilation: ", e.getMessage());
        }
      }

      return lazyJavacTask;
    }

    private JarBuilder newJarBuilder(JarParameters jarParameters) {
      JarBuilder jarBuilder = new JarBuilder();
      Objects.requireNonNull(inMemoryFileManager).writeToJar(jarBuilder);
      AbsPath rootPath = context.getRuleCellRoot();
      return jarBuilder
          .setObserver(new LoggingJarBuilderObserver(context.getEventSink()))
          .setEntriesToJar(jarParameters.getEntriesToJar().stream().map(rootPath::resolve))
          .setOverrideEntriesToJar(
              jarParameters.getOverrideEntriesToJar().stream().map(rootPath::resolve))
          .setMainClass(jarParameters.getMainClass().orElse(null))
          .setManifestFile(
              jarParameters
                  .getManifestFile()
                  .map(rootPath::resolve)
                  .map(AbsPath::getPath)
                  .orElse(null))
          .setShouldMergeManifests(true)
          .setRemoveEntryPredicate(jarParameters.getRemoveEntryPredicate());
    }

    private Iterable<? extends JavaFileObject> createCompilationUnits(
        StandardJavaFileManager fileManager,
        Function<RelPath, AbsPath> absolutifier,
        Set<RelPath> javaSourceFilePaths)
        throws IOException {
      List<JavaFileObject> compilationUnits = new ArrayList<>();
      boolean seenZipOrJarSources = false;
      for (RelPath path : javaSourceFilePaths) {
        String pathString = path.toString();
        if (pathString.endsWith(".java")) {
          // For an ordinary .java file, create a corresponding JavaFileObject.
          Iterable<? extends JavaFileObject> javaFileObjects =
              fileManager.getJavaFileObjects(absolutifier.apply(path).toFile());
          compilationUnits.add(Iterables.getOnlyElement(javaFileObjects));
        } else if (pathString.endsWith(JavaPaths.SRC_ZIP)
            || pathString.endsWith(JavaPaths.SRC_JAR)) {
          // For a Zip of .java files, create a JavaFileObject for each .java entry.
          ZipFile zipFile = new ZipFile(absolutifier.apply(path).toFile());
          boolean hasZipFileBeenUsed = false;
          seenZipOrJarSources = true;
          for (Enumeration<? extends ZipEntry> entries = zipFile.entries();
              entries.hasMoreElements(); ) {
            ZipEntry entry = entries.nextElement();
            if (!entry.getName().endsWith(".java")) {
              continue;
            }

            hasZipFileBeenUsed = true;
            compilationUnits.add(new ZipEntryJavaFileObject(zipFile, entry));
          }

          if (!hasZipFileBeenUsed) {
            zipFile.close();
          }
        }
      }

      // Bail now if none of the given files were Java files - we don't want to run non-Java files
      // through javac and it's unlikely that this was the user's intention.
      //
      // If we've seen a source zip file that was empty, allow it. Annotation processor rules
      // generate zip files of java sources which may not contain any java files if there were no
      // annotations to process. Since this process is automated, it doesn't make sense to raise
      // a human-facing error message in that case.
      if (!seenZipOrJarSources && !javaSourceFilePaths.isEmpty() && compilationUnits.isEmpty()) {
        throw new HumanReadableException(NO_JAVA_FILES_ERROR_MESSAGE);
      }
      return compilationUnits;
    }
  }

  private static SourceVersion getTargetVersion(Iterable<String> options) {
    boolean foundTarget = false;
    for (String option : options) {
      if (option.equals("-target")) {
        foundTarget = true;
      } else if (foundTarget) {
        return SourceVersionUtils.getSourceVersionFromTarget(option);
      }
    }

    throw new AssertionError("Unreachable code");
  }
}
