package com.mulesoft.connectors.inference.internal.llmmodels;

public interface ModelCapabilities {

  ModelCapabilitySet getCapabilities();

  default String getModelName() {
    return getCapabilities().modelName();
  }

  default boolean supportsTextGeneration() {
    return getCapabilities().supportsTextGeneration();
  }

  default boolean supportsModeration() {
    return getCapabilities().supportsModeration();
  }

  default boolean supportsImageGeneration() {
    return getCapabilities().supportsImageGeneration();
  }

  default boolean supportsVision() {
    return getCapabilities().supportsVision();
  }
}
