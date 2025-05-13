package com.mulesoft.connectors.internal.api.metadata;

import java.io.Serializable;
import java.util.Map;

public class LLMResponseAttributes implements Serializable {

  private final TokenUsage tokenUsage;
  private final Map<String, String> additionalAttributes;

  public LLMResponseAttributes(TokenUsage tokenUsage, Map<String, String> additionalAttributes) {
    this.tokenUsage = tokenUsage;
    this.additionalAttributes = additionalAttributes;
  }

  public TokenUsage getTokenUsage() {
    return tokenUsage;
  }

  public Map<String, String> getAdditionalAttributes() {
    return additionalAttributes;
  }
}
