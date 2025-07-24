package com.mulesoft.connectors.inference.internal.llmmodels;

public record ModelCapabilitySet(String modelName,boolean supportsTextGeneration,boolean supportsModeration,boolean supportsImageGeneration,boolean supportsVision){}
