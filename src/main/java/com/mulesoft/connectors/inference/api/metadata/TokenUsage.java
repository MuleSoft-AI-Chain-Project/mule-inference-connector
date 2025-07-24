package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.Objects;

public class TokenUsage implements Serializable {

  private final int inputCount;
  private final int outputCount;
  private final int totalCount;

  public TokenUsage(int inputCount, int outputCount, int totalCount) {
    this.inputCount = inputCount;
    this.outputCount = outputCount;
    this.totalCount = totalCount;
  }

  public int getInputCount() {
    return inputCount;
  }

  public int getOutputCount() {
    return outputCount;
  }

  public int getTotalCount() {
    return totalCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TokenUsage that = (TokenUsage) o;
    return inputCount == that.inputCount && outputCount == that.outputCount && totalCount == that.totalCount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputCount, outputCount, totalCount);
  }
}
