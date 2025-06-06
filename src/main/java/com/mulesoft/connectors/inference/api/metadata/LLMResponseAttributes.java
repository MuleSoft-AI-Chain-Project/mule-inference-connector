package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.Objects;

public class LLMResponseAttributes implements Serializable {

  private final TokenUsage tokenUsage;
  private final AdditionalAttributes additionalAttributes;

  public LLMResponseAttributes(TokenUsage tokenUsage, AdditionalAttributes additionalAttributes) {
    this.tokenUsage = tokenUsage;
    this.additionalAttributes = additionalAttributes;
  }

  public TokenUsage getTokenUsage() {
    return tokenUsage;
  }

  public AdditionalAttributes getAdditionalAttributes() {
    return additionalAttributes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    LLMResponseAttributes that = (LLMResponseAttributes) o;
    return Objects.equals(tokenUsage, that.tokenUsage) && Objects.equals(additionalAttributes, that.additionalAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokenUsage, additionalAttributes);
  }

  @Override
  public String toString() {
    return "LLMResponseAttributes{" +
        "tokenUsage=" + tokenUsage +
        ", additionalAttributes=" + additionalAttributes +
        '}';
  }
}
