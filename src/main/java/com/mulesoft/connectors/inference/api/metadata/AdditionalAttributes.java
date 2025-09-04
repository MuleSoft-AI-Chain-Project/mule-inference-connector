package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdditionalAttributes implements Serializable {

  private final String id;
  private final String model;
  private final String finishReason;
  private final Map<String, FilterResult> contentFilterResults;
  private final List<PromptFilterResult> promptFilterResults;

  public AdditionalAttributes(String id, String model, String finishReason, Map<String, FilterResult> contentFilterResults,
                              List<PromptFilterResult> promptFilterResults) {
    this.id = id;
    this.model = model;
    this.finishReason = finishReason;
    this.contentFilterResults = contentFilterResults;
    this.promptFilterResults = promptFilterResults;
  }

  public String getId() {
    return id;
  }

  public String getModel() {
    return model;
  }

  public String getFinishReason() {
    return finishReason;
  }

  public Map<String, FilterResult> getContentFilterResults() {
    return contentFilterResults;
  }

  public List<PromptFilterResult> getPromptFilterResults() {
    return promptFilterResults;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    AdditionalAttributes that = (AdditionalAttributes) o;
    return Objects.equals(id, that.id) && Objects.equals(model, that.model) && Objects.equals(finishReason, that.finishReason)
        && Objects.equals(contentFilterResults, that.contentFilterResults)
        && Objects.equals(promptFilterResults, that.promptFilterResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, model, finishReason, contentFilterResults, promptFilterResults);
  }

  @Override
  public String toString() {
    return "AdditionalAttributes{" +
        "id='" + id + '\'' +
        ", model='" + model + '\'' +
        ", finishReason='" + finishReason + '\'' +
        ", contentFilterResults=" + contentFilterResults +
        ", promptFilterResults=" + promptFilterResults +
        '}';
  }
}
