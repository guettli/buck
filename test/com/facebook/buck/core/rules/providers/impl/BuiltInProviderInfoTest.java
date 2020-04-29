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

package com.facebook.buck.core.rules.providers.impl;

import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.facebook.buck.core.rules.providers.annotations.ImmutableInfo;
import com.facebook.buck.core.starlark.compatible.BuckSkylarkTypes;
import com.facebook.buck.core.starlark.compatible.BuckStarlark;
import com.facebook.buck.core.starlark.compatible.TestMutableEnv;
import com.facebook.buck.core.starlark.testutil.TestStarlarkParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.devtools.build.lib.events.Location;
import com.google.devtools.build.lib.syntax.Dict;
import com.google.devtools.build.lib.syntax.EvalException;
import com.google.devtools.build.lib.syntax.EvalUtils;
import com.google.devtools.build.lib.syntax.Module;
import com.google.devtools.build.lib.syntax.Mutability;
import com.google.devtools.build.lib.syntax.ParserInput;
import com.google.devtools.build.lib.syntax.Starlark;
import com.google.devtools.build.lib.syntax.StarlarkList;
import com.google.devtools.build.lib.syntax.StarlarkThread;
import com.google.devtools.build.lib.syntax.SyntaxError;
import com.google.devtools.build.lib.vfs.PathFragment;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import org.hamcrest.Matchers;
import org.immutables.value.Value;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BuiltInProviderInfoTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @ImmutableInfo(
      args = {"str", "my_info"},
      defaultSkylarkValues = {"\"default value\"", "1"})
  public abstract static class SomeInfo extends BuiltInProviderInfo<SomeInfo> {
    public static final BuiltInProvider<SomeInfo> PROVIDER =
        BuiltInProvider.of(ImmutableSomeInfo.class);

    public abstract String str();

    public abstract int myInfo();
  }

  @ImmutableInfo(
      args = {"str"},
      defaultSkylarkValues = {""})
  public abstract static class OtherInfo extends BuiltInProviderInfo<OtherInfo> {
    public static final BuiltInProvider<OtherInfo> PROVIDER =
        BuiltInProvider.of(ImmutableOtherInfo.class);

    public abstract String str();
  }

  @ImmutableInfo(
      args = {"set"},
      defaultSkylarkValues = "[]")
  public abstract static class InfoWithSet extends BuiltInProviderInfo<InfoWithSet> {
    public static final BuiltInProvider<InfoWithSet> PROVIDER =
        BuiltInProvider.of(ImmutableInfoWithSet.class);

    @Value.Parameter(order = 0)
    public abstract Set<String> set();
  }

  @ImmutableInfo(
      args = {"map"},
      defaultSkylarkValues = "{}")
  public abstract static class InfoWithMap extends BuiltInProviderInfo<InfoWithMap> {
    public static final BuiltInProvider<InfoWithMap> PROVIDER =
        BuiltInProvider.of(ImmutableInfoWithMap.class);

    @Value.Parameter(order = 0)
    public abstract Dict<String, Integer> map();
  }

  @ImmutableInfo(args = {"val"})
  public abstract static class InfoWithNoDefaultValOnAnnotation
      extends BuiltInProviderInfo<InfoWithNoDefaultValOnAnnotation> {
    public static final BuiltInProvider<InfoWithNoDefaultValOnAnnotation> PROVIDER =
        BuiltInProvider.of(ImmutableInfoWithNoDefaultValOnAnnotation.class);

    public abstract Integer val();
  }

  @ImmutableInfo(
      args = {"str_list", "my_info"},
      defaultSkylarkValues = {"{\"foo\":\"bar\"}", "1"})
  public abstract static class SomeInfoWithInstantiate
      extends BuiltInProviderInfo<SomeInfoWithInstantiate> {
    public static final BuiltInProvider<SomeInfoWithInstantiate> PROVIDER =
        BuiltInProvider.of(ImmutableSomeInfoWithInstantiate.class);

    public abstract ImmutableList<String> str_list();

    public abstract String myInfo();

    public static SomeInfoWithInstantiate instantiateFromSkylark(Dict<String, String> s, int myInfo)
        throws EvalException {
      Map<String, String> validated = s.getContents(String.class, String.class, "stuff");
      return new ImmutableSomeInfoWithInstantiate(
          ImmutableList.copyOf(validated.keySet()), Integer.toString(myInfo));
    }
  }

  @ImmutableInfo(
      args = {"str_list", "my_info"},
      defaultSkylarkValues = {"{\"foo\":\"bar\"}", "1"})
  public abstract static class SomeInfoWithInstantiateAndLocation
      extends BuiltInProviderInfo<SomeInfoWithInstantiateAndLocation> {
    public static final BuiltInProvider<SomeInfoWithInstantiateAndLocation> PROVIDER =
        BuiltInProvider.of(ImmutableSomeInfoWithInstantiateAndLocation.class);

    public abstract ImmutableList<String> str_list();

    public abstract String myInfo();

    public abstract Location location();

    public static SomeInfoWithInstantiateAndLocation instantiateFromSkylark(
        Dict<String, String> s, int myInfo, Location location) throws EvalException {
      Map<String, String> validated = s.getContents(String.class, String.class, "stuff");
      return new ImmutableSomeInfoWithInstantiateAndLocation(
          ImmutableList.copyOf(validated.keySet()), Integer.toString(myInfo), location);
    }
  }

  @ImmutableInfo(
      args = {"noneable_val", "val"},
      noneable = {"noneable_val"})
  public abstract static class SomeInfoWithNoneable
      extends BuiltInProviderInfo<SomeInfoWithNoneable> {
    public static final BuiltInProvider<SomeInfoWithNoneable> PROVIDER =
        BuiltInProvider.of(ImmutableSomeInfoWithNoneable.class);

    public abstract Object noneableVal();

    public abstract Object val();

    public static SomeInfoWithNoneable instantiateFromSkylark(Object noneableVal, Object val) {
      return new ImmutableSomeInfoWithNoneable(noneableVal, val);
    }
  }

  @ImmutableInfo(args = {"some_val"})
  public abstract static class SomeInfoWithNonAbstractMethod
      extends BuiltInProviderInfo<SomeInfoWithNonAbstractMethod> {
    public static final BuiltInProvider<SomeInfoWithNonAbstractMethod> PROVIDER =
        BuiltInProvider.of(ImmutableSomeInfoWithNonAbstractMethod.class);

    public abstract String someVal();

    public String getSomeComputedValue() {
      return "bleh";
    }
  }

  @ImmutableInfo(args = {"str_list", "other_list", "val"})
  public abstract static class SomeInfoWithMutableAndImmutable
      extends BuiltInProviderInfo<SomeInfoWithMutableAndImmutable> {
    public static final BuiltInProvider<SomeInfoWithMutableAndImmutable> PROVIDER =
        BuiltInProvider.of(ImmutableSomeInfoWithMutableAndImmutable.class);

    public abstract StarlarkList<String> strList();

    public abstract ImmutableList<Object> otherList();

    public abstract String val();

    public static SomeInfoWithMutableAndImmutable instantiateFromSkylark(
        StarlarkList<String> strList, StarlarkList<Object> otherList, String val) {
      return new ImmutableSomeInfoWithMutableAndImmutable(
          strList, otherList.getImmutableList(), val);
    }
  }

  @Test
  public void someInfoProviderCreatesCorrectInfo()
      throws IllegalAccessException, InstantiationException, InvocationTargetException,
          InterruptedException, EvalException, SyntaxError {
    SomeInfo someInfo1 = new ImmutableSomeInfo("a", 1);
    assertEquals("a", someInfo1.str());
    assertEquals(1, someInfo1.myInfo());

    SomeInfo someInfo2 = someInfo1.getProvider().createInfo("b", 2);
    assertEquals("b", someInfo2.str());
    assertEquals(2, someInfo2.myInfo());

    SomeInfo someInfo3 = SomeInfo.PROVIDER.createInfo("c", 3);
    assertEquals("c", someInfo3.str());
    assertEquals(3, someInfo3.myInfo());

    Object o;
    try (Mutability mutability = Mutability.create("providertest")) {
      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(SomeInfo.PROVIDER.getName(), SomeInfo.PROVIDER)))
              .build();

      o = TestStarlarkParser.eval(env, "SomeInfo(str='d', my_info=4)");
    }

    assertThat(o, Matchers.instanceOf(SomeInfo.class));
    SomeInfo someInfo4 = (SomeInfo) o;
    assertEquals("d", someInfo4.str());
    assertEquals(4, someInfo4.myInfo());
  }

  @Test
  public void infoWithSetCanBeCreatedProperly()
      throws IllegalAccessException, InstantiationException, InvocationTargetException {
    InfoWithSet someInfo1 = new ImmutableInfoWithSet(ImmutableSet.of("a"));
    assertEquals(ImmutableSet.of("a"), someInfo1.set());

    InfoWithSet someInfo2 = someInfo1.getProvider().createInfo(ImmutableSet.of("b"));
    assertEquals(ImmutableSet.of("b"), someInfo2.set());

    InfoWithSet someInfo3 = InfoWithSet.PROVIDER.createInfo(ImmutableSet.of());
    assertEquals(ImmutableSet.of(), someInfo3.set());
  }

  @Test
  public void infoWithMapCanBeCreatedProperly()
      throws IllegalAccessException, InstantiationException, InvocationTargetException {
    InfoWithMap someInfo1 = new ImmutableInfoWithMap(Dict.of(null, "a", 1));
    assertEquals(Dict.of(null, "a", 1), someInfo1.map());

    InfoWithMap someInfo2 = someInfo1.getProvider().createInfo(Dict.of(null, "b", 2));
    assertEquals(Dict.of(null, "b", 2), someInfo2.map());

    InfoWithMap someInfo3 = InfoWithMap.PROVIDER.createInfo(Dict.of(null));
    assertEquals(Dict.<String, Integer>of(null), someInfo3.map());
  }

  @Test
  public void differentInfoInstanceProviderKeyEquals()
      throws IllegalAccessException, InstantiationException, InvocationTargetException {
    SomeInfo someInfo1 = new ImmutableSomeInfo("a", 1);

    SomeInfo someInfo2 = SomeInfo.PROVIDER.createInfo("b", 2);

    assertEquals(SomeInfo.PROVIDER.getKey(), someInfo1.getProvider().getKey());
    assertEquals(someInfo1.getProvider().getKey(), someInfo2.getProvider().getKey());
  }

  @Test
  public void differentInfoTypeProviderKeyNotEquals() {
    assertNotEquals(SomeInfo.PROVIDER.getKey(), OtherInfo.PROVIDER.getKey());
  }

  @Test
  public void defaultValuesWorkInStarlarkContext()
      throws InterruptedException, EvalException, SyntaxError {

    try (Mutability mutability = Mutability.create("test")) {
      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          ImmutableSomeInfo.PROVIDER.getName(), ImmutableSomeInfo.PROVIDER)))
              .build();

      assertEquals(
          new ImmutableSomeInfo("default value", 2),
          TestStarlarkParser.eval(env, ImmutableSomeInfo.PROVIDER.getName() + "(my_info=2)"));
    }
  }

  @Test
  public void infoWithNoDefaultValueOnAnnotationWorks()
      throws InterruptedException, EvalException, SyntaxError {

    try (Mutability mutability = Mutability.create("test")) {
      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          InfoWithNoDefaultValOnAnnotation.PROVIDER.getName(),
                          InfoWithNoDefaultValOnAnnotation.PROVIDER)))
              .build();

      assertEquals(
          new ImmutableInfoWithNoDefaultValOnAnnotation(2),
          TestStarlarkParser.eval(
              env, ImmutableInfoWithNoDefaultValOnAnnotation.PROVIDER.getName() + "(val=2)"));
    }
  }

  @Test
  public void instantiatesFromStaticMethodIfPresent()
      throws InterruptedException, EvalException, SyntaxError {
    Object o;
    try (Mutability mutability = Mutability.create("providertest")) {
      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          SomeInfoWithInstantiate.PROVIDER.getName(),
                          SomeInfoWithInstantiate.PROVIDER)))
              .build();

      o =
          TestStarlarkParser.eval(
              env, "SomeInfoWithInstantiate(str_list={'d': 'd_value'}, my_info=4)");
    }

    assertThat(o, Matchers.instanceOf(SomeInfoWithInstantiate.class));
    SomeInfoWithInstantiate someInfo4 = (SomeInfoWithInstantiate) o;
    assertEquals(ImmutableList.of("d"), someInfo4.str_list());
    assertEquals("4", someInfo4.myInfo());
  }

  @Test
  public void instantiatesFromStaticMethodWithDefaultValuesIfPresent()
      throws InterruptedException, EvalException, SyntaxError {
    Object o;
    try (Mutability mutability = Mutability.create("providertest")) {
      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          SomeInfoWithInstantiate.PROVIDER.getName(),
                          SomeInfoWithInstantiate.PROVIDER)))
              .build();

      o = TestStarlarkParser.eval(env, "SomeInfoWithInstantiate(my_info=4)");
    }

    assertThat(o, Matchers.instanceOf(SomeInfoWithInstantiate.class));
    SomeInfoWithInstantiate someInfo4 = (SomeInfoWithInstantiate) o;
    assertEquals(ImmutableList.of("foo"), someInfo4.str_list());
    assertEquals("4", someInfo4.myInfo());
  }

  @Test
  public void validatesTypesWhenInstantiatingFromStaticMethod()
      throws InterruptedException, EvalException, SyntaxError {
    try (Mutability mutability = Mutability.create("providertest")) {
      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          SomeInfoWithInstantiate.PROVIDER.getName(),
                          SomeInfoWithInstantiate.PROVIDER)))
              .build();

      thrown.expect(EvalException.class);
      thrown.expectMessage("expected value of type 'int'");

      TestStarlarkParser.eval(env, "SomeInfoWithInstantiate(my_info='not a number')");
    }
  }

  @Test
  public void passesLocationWhenInstantiatingFromStaticMethod()
      throws InterruptedException, EvalException, SyntaxError {
    Location location =
        Location.fromPathAndStartColumn(
            PathFragment.create("foo/bar.bzl"), 0, 0, new Location.LineAndColumn(1, 1));
    Object o;
    try (Mutability mutability = Mutability.create("providertest")) {

      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          SomeInfoWithInstantiateAndLocation.PROVIDER.getName(),
                          SomeInfoWithInstantiateAndLocation.PROVIDER)))
              .build();

      o =
          EvalUtils.execAndEvalOptionalFinalExpression(
              ParserInput.create(
                  "SomeInfoWithInstantiateAndLocation(my_info=1)",
                  PathFragment.create("foo/bar.bzl")),
              env);
    }

    assertThat(o, Matchers.instanceOf(SomeInfoWithInstantiateAndLocation.class));
    SomeInfoWithInstantiateAndLocation someInfo = (SomeInfoWithInstantiateAndLocation) o;
    assertEquals(ImmutableList.of("foo"), someInfo.str_list());
    assertEquals("1", someInfo.myInfo());
    assertEquals(location.getPath(), someInfo.location().getPath());
    assertEquals(location.getStartLineAndColumn(), someInfo.location().getStartLineAndColumn());
  }

  @Test
  public void allowsNoneAsAParamToStaticMethod()
      throws InterruptedException, EvalException, SyntaxError {
    try (Mutability mutability = Mutability.create("providertest")) {

      StarlarkThread env =
          StarlarkThread.builder(mutability)
              .setSemantics(BuckStarlark.BUCK_STARLARK_SEMANTICS)
              .setGlobals(
                  Module.createForBuiltins(
                      ImmutableMap.of(
                          SomeInfoWithNoneable.PROVIDER.getName(),
                          SomeInfoWithNoneable.PROVIDER,
                          "None",
                          Starlark.NONE)))
              .build();

      Object none = TestStarlarkParser.eval(env, "SomeInfoWithNoneable(noneable_val=None, val=1)");
      Object strValue =
          TestStarlarkParser.eval(env, "SomeInfoWithNoneable(noneable_val=\"foo\", val=1)");

      assertThat(none, Matchers.instanceOf(SomeInfoWithNoneable.class));
      assertThat(strValue, Matchers.instanceOf(SomeInfoWithNoneable.class));

      assertEquals(Starlark.NONE, ((SomeInfoWithNoneable) none).noneableVal());
      assertEquals("foo", ((SomeInfoWithNoneable) strValue).noneableVal());

      thrown.expect(EvalException.class);
      thrown.expectMessage("cannot be None");
      TestStarlarkParser.eval(env, "SomeInfoWithNoneable(noneable_val=None, val=None)");
    }
  }

  @Test
  public void fieldNamesComeFromAbstractMethodsOnly()
      throws IllegalAccessException, InstantiationException, InvocationTargetException {
    SomeInfoWithNonAbstractMethod providerInfo =
        SomeInfoWithNonAbstractMethod.PROVIDER.createInfo("some string");

    assertEquals(ImmutableSet.of("some_val"), providerInfo.getFieldNames());
  }

  @Test
  public void isImmutableWorks() throws Exception {

    String buildFile =
        "SomeInfoWithMutableAndImmutable("
            + "val='foo', str_list=mutable_list, other_list=[1,mutable_list])";

    SomeInfoWithMutableAndImmutable out;

    try (TestMutableEnv env =
        new TestMutableEnv(
            ImmutableMap.of(
                "SomeInfoWithMutableAndImmutable", SomeInfoWithMutableAndImmutable.PROVIDER))) {
      StarlarkList<Integer> mutableList = StarlarkList.of(env.getEnv().mutability(), 1, 2, 3);
      env.getEnv().update("mutable_list", mutableList);

      out = (SomeInfoWithMutableAndImmutable) TestStarlarkParser.eval(env.getEnv(), buildFile);

      assertNotNull(out);
      assertFalse(out.isImmutable());
      assertFalse(BuckSkylarkTypes.isImmutable(out.strList()));
      assertFalse(BuckSkylarkTypes.isImmutable(out.otherList()));
      assertTrue(BuckSkylarkTypes.isImmutable(out.val()));
    }

    assertTrue(out.isImmutable());
    assertTrue(BuckSkylarkTypes.isImmutable(out.strList()));
    assertTrue(BuckSkylarkTypes.isImmutable(out.otherList()));
    assertTrue(BuckSkylarkTypes.isImmutable(out.val()));
  }
}
