// @generated
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: remoteexecution/proto/metadata.proto

package com.facebook.buck.remoteexecution.proto;

/**
 * Protobuf type {@code facebook.remote_execution.StorageStats}
 */
@javax.annotation.Generated(value="protoc", comments="annotations:StorageStats.java.pb.meta")
public  final class StorageStats extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:facebook.remote_execution.StorageStats)
    StorageStatsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use StorageStats.newBuilder() to construct.
  private StorageStats(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private StorageStats() {
    perBackendStats_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private StorageStats(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            totalBytes_ = input.readUInt64();
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) != 0)) {
              perBackendStats_ = new java.util.ArrayList<com.facebook.buck.remoteexecution.proto.StorageBackendStats>();
              mutable_bitField0_ |= 0x00000002;
            }
            perBackendStats_.add(
                input.readMessage(com.facebook.buck.remoteexecution.proto.StorageBackendStats.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        perBackendStats_ = java.util.Collections.unmodifiableList(perBackendStats_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_StorageStats_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_StorageStats_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.facebook.buck.remoteexecution.proto.StorageStats.class, com.facebook.buck.remoteexecution.proto.StorageStats.Builder.class);
  }

  private int bitField0_;
  public static final int TOTAL_BYTES_FIELD_NUMBER = 1;
  private long totalBytes_;
  /**
   * <code>uint64 total_bytes = 1;</code>
   */
  public long getTotalBytes() {
    return totalBytes_;
  }

  public static final int PER_BACKEND_STATS_FIELD_NUMBER = 2;
  private java.util.List<com.facebook.buck.remoteexecution.proto.StorageBackendStats> perBackendStats_;
  /**
   * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
   */
  public java.util.List<com.facebook.buck.remoteexecution.proto.StorageBackendStats> getPerBackendStatsList() {
    return perBackendStats_;
  }
  /**
   * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
   */
  public java.util.List<? extends com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder> 
      getPerBackendStatsOrBuilderList() {
    return perBackendStats_;
  }
  /**
   * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
   */
  public int getPerBackendStatsCount() {
    return perBackendStats_.size();
  }
  /**
   * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
   */
  public com.facebook.buck.remoteexecution.proto.StorageBackendStats getPerBackendStats(int index) {
    return perBackendStats_.get(index);
  }
  /**
   * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
   */
  public com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder getPerBackendStatsOrBuilder(
      int index) {
    return perBackendStats_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (totalBytes_ != 0L) {
      output.writeUInt64(1, totalBytes_);
    }
    for (int i = 0; i < perBackendStats_.size(); i++) {
      output.writeMessage(2, perBackendStats_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (totalBytes_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, totalBytes_);
    }
    for (int i = 0; i < perBackendStats_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, perBackendStats_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.facebook.buck.remoteexecution.proto.StorageStats)) {
      return super.equals(obj);
    }
    com.facebook.buck.remoteexecution.proto.StorageStats other = (com.facebook.buck.remoteexecution.proto.StorageStats) obj;

    if (getTotalBytes()
        != other.getTotalBytes()) return false;
    if (!getPerBackendStatsList()
        .equals(other.getPerBackendStatsList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TOTAL_BYTES_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTotalBytes());
    if (getPerBackendStatsCount() > 0) {
      hash = (37 * hash) + PER_BACKEND_STATS_FIELD_NUMBER;
      hash = (53 * hash) + getPerBackendStatsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.facebook.buck.remoteexecution.proto.StorageStats parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.facebook.buck.remoteexecution.proto.StorageStats prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code facebook.remote_execution.StorageStats}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:facebook.remote_execution.StorageStats)
      com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_StorageStats_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_StorageStats_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.facebook.buck.remoteexecution.proto.StorageStats.class, com.facebook.buck.remoteexecution.proto.StorageStats.Builder.class);
    }

    // Construct using com.facebook.buck.remoteexecution.proto.StorageStats.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getPerBackendStatsFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      totalBytes_ = 0L;

      if (perBackendStatsBuilder_ == null) {
        perBackendStats_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        perBackendStatsBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_StorageStats_descriptor;
    }

    @java.lang.Override
    public com.facebook.buck.remoteexecution.proto.StorageStats getDefaultInstanceForType() {
      return com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance();
    }

    @java.lang.Override
    public com.facebook.buck.remoteexecution.proto.StorageStats build() {
      com.facebook.buck.remoteexecution.proto.StorageStats result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.facebook.buck.remoteexecution.proto.StorageStats buildPartial() {
      com.facebook.buck.remoteexecution.proto.StorageStats result = new com.facebook.buck.remoteexecution.proto.StorageStats(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.totalBytes_ = totalBytes_;
      if (perBackendStatsBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          perBackendStats_ = java.util.Collections.unmodifiableList(perBackendStats_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.perBackendStats_ = perBackendStats_;
      } else {
        result.perBackendStats_ = perBackendStatsBuilder_.build();
      }
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.facebook.buck.remoteexecution.proto.StorageStats) {
        return mergeFrom((com.facebook.buck.remoteexecution.proto.StorageStats)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.facebook.buck.remoteexecution.proto.StorageStats other) {
      if (other == com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance()) return this;
      if (other.getTotalBytes() != 0L) {
        setTotalBytes(other.getTotalBytes());
      }
      if (perBackendStatsBuilder_ == null) {
        if (!other.perBackendStats_.isEmpty()) {
          if (perBackendStats_.isEmpty()) {
            perBackendStats_ = other.perBackendStats_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensurePerBackendStatsIsMutable();
            perBackendStats_.addAll(other.perBackendStats_);
          }
          onChanged();
        }
      } else {
        if (!other.perBackendStats_.isEmpty()) {
          if (perBackendStatsBuilder_.isEmpty()) {
            perBackendStatsBuilder_.dispose();
            perBackendStatsBuilder_ = null;
            perBackendStats_ = other.perBackendStats_;
            bitField0_ = (bitField0_ & ~0x00000002);
            perBackendStatsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getPerBackendStatsFieldBuilder() : null;
          } else {
            perBackendStatsBuilder_.addAllMessages(other.perBackendStats_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.facebook.buck.remoteexecution.proto.StorageStats parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.facebook.buck.remoteexecution.proto.StorageStats) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private long totalBytes_ ;
    /**
     * <code>uint64 total_bytes = 1;</code>
     */
    public long getTotalBytes() {
      return totalBytes_;
    }
    /**
     * <code>uint64 total_bytes = 1;</code>
     */
    public Builder setTotalBytes(long value) {
      
      totalBytes_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 total_bytes = 1;</code>
     */
    public Builder clearTotalBytes() {
      
      totalBytes_ = 0L;
      onChanged();
      return this;
    }

    private java.util.List<com.facebook.buck.remoteexecution.proto.StorageBackendStats> perBackendStats_ =
      java.util.Collections.emptyList();
    private void ensurePerBackendStatsIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        perBackendStats_ = new java.util.ArrayList<com.facebook.buck.remoteexecution.proto.StorageBackendStats>(perBackendStats_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.facebook.buck.remoteexecution.proto.StorageBackendStats, com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder, com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder> perBackendStatsBuilder_;

    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public java.util.List<com.facebook.buck.remoteexecution.proto.StorageBackendStats> getPerBackendStatsList() {
      if (perBackendStatsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(perBackendStats_);
      } else {
        return perBackendStatsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public int getPerBackendStatsCount() {
      if (perBackendStatsBuilder_ == null) {
        return perBackendStats_.size();
      } else {
        return perBackendStatsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageBackendStats getPerBackendStats(int index) {
      if (perBackendStatsBuilder_ == null) {
        return perBackendStats_.get(index);
      } else {
        return perBackendStatsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder setPerBackendStats(
        int index, com.facebook.buck.remoteexecution.proto.StorageBackendStats value) {
      if (perBackendStatsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePerBackendStatsIsMutable();
        perBackendStats_.set(index, value);
        onChanged();
      } else {
        perBackendStatsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder setPerBackendStats(
        int index, com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder builderForValue) {
      if (perBackendStatsBuilder_ == null) {
        ensurePerBackendStatsIsMutable();
        perBackendStats_.set(index, builderForValue.build());
        onChanged();
      } else {
        perBackendStatsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder addPerBackendStats(com.facebook.buck.remoteexecution.proto.StorageBackendStats value) {
      if (perBackendStatsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePerBackendStatsIsMutable();
        perBackendStats_.add(value);
        onChanged();
      } else {
        perBackendStatsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder addPerBackendStats(
        int index, com.facebook.buck.remoteexecution.proto.StorageBackendStats value) {
      if (perBackendStatsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePerBackendStatsIsMutable();
        perBackendStats_.add(index, value);
        onChanged();
      } else {
        perBackendStatsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder addPerBackendStats(
        com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder builderForValue) {
      if (perBackendStatsBuilder_ == null) {
        ensurePerBackendStatsIsMutable();
        perBackendStats_.add(builderForValue.build());
        onChanged();
      } else {
        perBackendStatsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder addPerBackendStats(
        int index, com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder builderForValue) {
      if (perBackendStatsBuilder_ == null) {
        ensurePerBackendStatsIsMutable();
        perBackendStats_.add(index, builderForValue.build());
        onChanged();
      } else {
        perBackendStatsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder addAllPerBackendStats(
        java.lang.Iterable<? extends com.facebook.buck.remoteexecution.proto.StorageBackendStats> values) {
      if (perBackendStatsBuilder_ == null) {
        ensurePerBackendStatsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, perBackendStats_);
        onChanged();
      } else {
        perBackendStatsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder clearPerBackendStats() {
      if (perBackendStatsBuilder_ == null) {
        perBackendStats_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        perBackendStatsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public Builder removePerBackendStats(int index) {
      if (perBackendStatsBuilder_ == null) {
        ensurePerBackendStatsIsMutable();
        perBackendStats_.remove(index);
        onChanged();
      } else {
        perBackendStatsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder getPerBackendStatsBuilder(
        int index) {
      return getPerBackendStatsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder getPerBackendStatsOrBuilder(
        int index) {
      if (perBackendStatsBuilder_ == null) {
        return perBackendStats_.get(index);  } else {
        return perBackendStatsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public java.util.List<? extends com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder> 
         getPerBackendStatsOrBuilderList() {
      if (perBackendStatsBuilder_ != null) {
        return perBackendStatsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(perBackendStats_);
      }
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder addPerBackendStatsBuilder() {
      return getPerBackendStatsFieldBuilder().addBuilder(
          com.facebook.buck.remoteexecution.proto.StorageBackendStats.getDefaultInstance());
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder addPerBackendStatsBuilder(
        int index) {
      return getPerBackendStatsFieldBuilder().addBuilder(
          index, com.facebook.buck.remoteexecution.proto.StorageBackendStats.getDefaultInstance());
    }
    /**
     * <code>repeated .facebook.remote_execution.StorageBackendStats per_backend_stats = 2;</code>
     */
    public java.util.List<com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder> 
         getPerBackendStatsBuilderList() {
      return getPerBackendStatsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.facebook.buck.remoteexecution.proto.StorageBackendStats, com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder, com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder> 
        getPerBackendStatsFieldBuilder() {
      if (perBackendStatsBuilder_ == null) {
        perBackendStatsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.facebook.buck.remoteexecution.proto.StorageBackendStats, com.facebook.buck.remoteexecution.proto.StorageBackendStats.Builder, com.facebook.buck.remoteexecution.proto.StorageBackendStatsOrBuilder>(
                perBackendStats_,
                ((bitField0_ & 0x00000002) != 0),
                getParentForChildren(),
                isClean());
        perBackendStats_ = null;
      }
      return perBackendStatsBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:facebook.remote_execution.StorageStats)
  }

  // @@protoc_insertion_point(class_scope:facebook.remote_execution.StorageStats)
  private static final com.facebook.buck.remoteexecution.proto.StorageStats DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.facebook.buck.remoteexecution.proto.StorageStats();
  }

  public static com.facebook.buck.remoteexecution.proto.StorageStats getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<StorageStats>
      PARSER = new com.google.protobuf.AbstractParser<StorageStats>() {
    @java.lang.Override
    public StorageStats parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new StorageStats(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<StorageStats> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<StorageStats> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.facebook.buck.remoteexecution.proto.StorageStats getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

