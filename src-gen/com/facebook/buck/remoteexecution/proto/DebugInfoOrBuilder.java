// @generated
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: remoteexecution/proto/metadata.proto

package com.facebook.buck.remoteexecution.proto;

@javax.annotation.Generated(value="protoc", comments="annotations:DebugInfoOrBuilder.java.pb.meta")
public interface DebugInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:facebook.remote_execution.DebugInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint32 pause_before_clean_timeout = 1 [deprecated = true];</code>
   */
  @java.lang.Deprecated int getPauseBeforeCleanTimeout();

  /**
   * <pre>
   * should skip cleanup on the worker
   * </pre>
   *
   * <code>bool skip_cleanup = 2;</code>
   */
  boolean getSkipCleanup();

  /**
   * <code>.facebook.remote_execution.DebugInfo.BpftraceFilter bpftrace_filter = 3;</code>
   */
  int getBpftraceFilterValue();
  /**
   * <code>.facebook.remote_execution.DebugInfo.BpftraceFilter bpftrace_filter = 3;</code>
   */
  com.facebook.buck.remoteexecution.proto.DebugInfo.BpftraceFilter getBpftraceFilter();
}
