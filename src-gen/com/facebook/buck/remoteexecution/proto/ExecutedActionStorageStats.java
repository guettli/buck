// @generated
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: remoteexecution/proto/metadata.proto

package com.facebook.buck.remoteexecution.proto;

/**
 * Protobuf type {@code facebook.remote_execution.ExecutedActionStorageStats}
 */
@javax.annotation.Generated(value="protoc", comments="annotations:ExecutedActionStorageStats.java.pb.meta")
public  final class ExecutedActionStorageStats extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:facebook.remote_execution.ExecutedActionStorageStats)
    ExecutedActionStorageStatsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ExecutedActionStorageStats.newBuilder() to construct.
  private ExecutedActionStorageStats(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ExecutedActionStorageStats() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ExecutedActionStorageStats(
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
          case 10: {
            com.facebook.buck.remoteexecution.proto.StorageStats.Builder subBuilder = null;
            if (downloads_ != null) {
              subBuilder = downloads_.toBuilder();
            }
            downloads_ = input.readMessage(com.facebook.buck.remoteexecution.proto.StorageStats.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(downloads_);
              downloads_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            com.facebook.buck.remoteexecution.proto.StorageStats.Builder subBuilder = null;
            if (uploads_ != null) {
              subBuilder = uploads_.toBuilder();
            }
            uploads_ = input.readMessage(com.facebook.buck.remoteexecution.proto.StorageStats.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(uploads_);
              uploads_ = subBuilder.buildPartial();
            }

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
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_ExecutedActionStorageStats_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_ExecutedActionStorageStats_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.class, com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.Builder.class);
  }

  public static final int DOWNLOADS_FIELD_NUMBER = 1;
  private com.facebook.buck.remoteexecution.proto.StorageStats downloads_;
  /**
   * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
   */
  public boolean hasDownloads() {
    return downloads_ != null;
  }
  /**
   * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
   */
  public com.facebook.buck.remoteexecution.proto.StorageStats getDownloads() {
    return downloads_ == null ? com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance() : downloads_;
  }
  /**
   * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
   */
  public com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder getDownloadsOrBuilder() {
    return getDownloads();
  }

  public static final int UPLOADS_FIELD_NUMBER = 2;
  private com.facebook.buck.remoteexecution.proto.StorageStats uploads_;
  /**
   * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
   */
  public boolean hasUploads() {
    return uploads_ != null;
  }
  /**
   * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
   */
  public com.facebook.buck.remoteexecution.proto.StorageStats getUploads() {
    return uploads_ == null ? com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance() : uploads_;
  }
  /**
   * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
   */
  public com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder getUploadsOrBuilder() {
    return getUploads();
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
    if (downloads_ != null) {
      output.writeMessage(1, getDownloads());
    }
    if (uploads_ != null) {
      output.writeMessage(2, getUploads());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (downloads_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getDownloads());
    }
    if (uploads_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getUploads());
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
    if (!(obj instanceof com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats)) {
      return super.equals(obj);
    }
    com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats other = (com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats) obj;

    if (hasDownloads() != other.hasDownloads()) return false;
    if (hasDownloads()) {
      if (!getDownloads()
          .equals(other.getDownloads())) return false;
    }
    if (hasUploads() != other.hasUploads()) return false;
    if (hasUploads()) {
      if (!getUploads()
          .equals(other.getUploads())) return false;
    }
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
    if (hasDownloads()) {
      hash = (37 * hash) + DOWNLOADS_FIELD_NUMBER;
      hash = (53 * hash) + getDownloads().hashCode();
    }
    if (hasUploads()) {
      hash = (37 * hash) + UPLOADS_FIELD_NUMBER;
      hash = (53 * hash) + getUploads().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parseFrom(
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
  public static Builder newBuilder(com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats prototype) {
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
   * Protobuf type {@code facebook.remote_execution.ExecutedActionStorageStats}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:facebook.remote_execution.ExecutedActionStorageStats)
      com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStatsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_ExecutedActionStorageStats_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_ExecutedActionStorageStats_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.class, com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.Builder.class);
    }

    // Construct using com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.newBuilder()
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
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (downloadsBuilder_ == null) {
        downloads_ = null;
      } else {
        downloads_ = null;
        downloadsBuilder_ = null;
      }
      if (uploadsBuilder_ == null) {
        uploads_ = null;
      } else {
        uploads_ = null;
        uploadsBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_ExecutedActionStorageStats_descriptor;
    }

    @java.lang.Override
    public com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats getDefaultInstanceForType() {
      return com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.getDefaultInstance();
    }

    @java.lang.Override
    public com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats build() {
      com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats buildPartial() {
      com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats result = new com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats(this);
      if (downloadsBuilder_ == null) {
        result.downloads_ = downloads_;
      } else {
        result.downloads_ = downloadsBuilder_.build();
      }
      if (uploadsBuilder_ == null) {
        result.uploads_ = uploads_;
      } else {
        result.uploads_ = uploadsBuilder_.build();
      }
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
      if (other instanceof com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats) {
        return mergeFrom((com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats other) {
      if (other == com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats.getDefaultInstance()) return this;
      if (other.hasDownloads()) {
        mergeDownloads(other.getDownloads());
      }
      if (other.hasUploads()) {
        mergeUploads(other.getUploads());
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
      com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.facebook.buck.remoteexecution.proto.StorageStats downloads_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.facebook.buck.remoteexecution.proto.StorageStats, com.facebook.buck.remoteexecution.proto.StorageStats.Builder, com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder> downloadsBuilder_;
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public boolean hasDownloads() {
      return downloadsBuilder_ != null || downloads_ != null;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageStats getDownloads() {
      if (downloadsBuilder_ == null) {
        return downloads_ == null ? com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance() : downloads_;
      } else {
        return downloadsBuilder_.getMessage();
      }
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public Builder setDownloads(com.facebook.buck.remoteexecution.proto.StorageStats value) {
      if (downloadsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        downloads_ = value;
        onChanged();
      } else {
        downloadsBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public Builder setDownloads(
        com.facebook.buck.remoteexecution.proto.StorageStats.Builder builderForValue) {
      if (downloadsBuilder_ == null) {
        downloads_ = builderForValue.build();
        onChanged();
      } else {
        downloadsBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public Builder mergeDownloads(com.facebook.buck.remoteexecution.proto.StorageStats value) {
      if (downloadsBuilder_ == null) {
        if (downloads_ != null) {
          downloads_ =
            com.facebook.buck.remoteexecution.proto.StorageStats.newBuilder(downloads_).mergeFrom(value).buildPartial();
        } else {
          downloads_ = value;
        }
        onChanged();
      } else {
        downloadsBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public Builder clearDownloads() {
      if (downloadsBuilder_ == null) {
        downloads_ = null;
        onChanged();
      } else {
        downloads_ = null;
        downloadsBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageStats.Builder getDownloadsBuilder() {
      
      onChanged();
      return getDownloadsFieldBuilder().getBuilder();
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder getDownloadsOrBuilder() {
      if (downloadsBuilder_ != null) {
        return downloadsBuilder_.getMessageOrBuilder();
      } else {
        return downloads_ == null ?
            com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance() : downloads_;
      }
    }
    /**
     * <code>.facebook.remote_execution.StorageStats downloads = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.facebook.buck.remoteexecution.proto.StorageStats, com.facebook.buck.remoteexecution.proto.StorageStats.Builder, com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder> 
        getDownloadsFieldBuilder() {
      if (downloadsBuilder_ == null) {
        downloadsBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.facebook.buck.remoteexecution.proto.StorageStats, com.facebook.buck.remoteexecution.proto.StorageStats.Builder, com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder>(
                getDownloads(),
                getParentForChildren(),
                isClean());
        downloads_ = null;
      }
      return downloadsBuilder_;
    }

    private com.facebook.buck.remoteexecution.proto.StorageStats uploads_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.facebook.buck.remoteexecution.proto.StorageStats, com.facebook.buck.remoteexecution.proto.StorageStats.Builder, com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder> uploadsBuilder_;
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public boolean hasUploads() {
      return uploadsBuilder_ != null || uploads_ != null;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageStats getUploads() {
      if (uploadsBuilder_ == null) {
        return uploads_ == null ? com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance() : uploads_;
      } else {
        return uploadsBuilder_.getMessage();
      }
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public Builder setUploads(com.facebook.buck.remoteexecution.proto.StorageStats value) {
      if (uploadsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        uploads_ = value;
        onChanged();
      } else {
        uploadsBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public Builder setUploads(
        com.facebook.buck.remoteexecution.proto.StorageStats.Builder builderForValue) {
      if (uploadsBuilder_ == null) {
        uploads_ = builderForValue.build();
        onChanged();
      } else {
        uploadsBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public Builder mergeUploads(com.facebook.buck.remoteexecution.proto.StorageStats value) {
      if (uploadsBuilder_ == null) {
        if (uploads_ != null) {
          uploads_ =
            com.facebook.buck.remoteexecution.proto.StorageStats.newBuilder(uploads_).mergeFrom(value).buildPartial();
        } else {
          uploads_ = value;
        }
        onChanged();
      } else {
        uploadsBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public Builder clearUploads() {
      if (uploadsBuilder_ == null) {
        uploads_ = null;
        onChanged();
      } else {
        uploads_ = null;
        uploadsBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageStats.Builder getUploadsBuilder() {
      
      onChanged();
      return getUploadsFieldBuilder().getBuilder();
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    public com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder getUploadsOrBuilder() {
      if (uploadsBuilder_ != null) {
        return uploadsBuilder_.getMessageOrBuilder();
      } else {
        return uploads_ == null ?
            com.facebook.buck.remoteexecution.proto.StorageStats.getDefaultInstance() : uploads_;
      }
    }
    /**
     * <code>.facebook.remote_execution.StorageStats uploads = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.facebook.buck.remoteexecution.proto.StorageStats, com.facebook.buck.remoteexecution.proto.StorageStats.Builder, com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder> 
        getUploadsFieldBuilder() {
      if (uploadsBuilder_ == null) {
        uploadsBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.facebook.buck.remoteexecution.proto.StorageStats, com.facebook.buck.remoteexecution.proto.StorageStats.Builder, com.facebook.buck.remoteexecution.proto.StorageStatsOrBuilder>(
                getUploads(),
                getParentForChildren(),
                isClean());
        uploads_ = null;
      }
      return uploadsBuilder_;
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


    // @@protoc_insertion_point(builder_scope:facebook.remote_execution.ExecutedActionStorageStats)
  }

  // @@protoc_insertion_point(class_scope:facebook.remote_execution.ExecutedActionStorageStats)
  private static final com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats();
  }

  public static com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ExecutedActionStorageStats>
      PARSER = new com.google.protobuf.AbstractParser<ExecutedActionStorageStats>() {
    @java.lang.Override
    public ExecutedActionStorageStats parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ExecutedActionStorageStats(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ExecutedActionStorageStats> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ExecutedActionStorageStats> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.facebook.buck.remoteexecution.proto.ExecutedActionStorageStats getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

