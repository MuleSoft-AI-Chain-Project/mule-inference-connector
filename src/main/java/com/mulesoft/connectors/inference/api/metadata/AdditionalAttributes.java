package com.mulesoft.connectors.inference.api.metadata;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import java.io.Serializable;
import java.util.Objects;



public class AdditionalAttributes implements Serializable {

  private final String id;
  private final String model;
  private final String finishReason;
  private final String nativeResponse;

  public AdditionalAttributes(String id, String model, String finishReason, String nativeResponse) {
    this.id = id;
    this.model = model;
    this.finishReason = finishReason;
    this.nativeResponse = nativeResponse;
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

  public String getNativeResponse() {
    return nativeResponse;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AdditionalAttributes that = (AdditionalAttributes) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(model, that.model) &&
        Objects.equals(finishReason, that.finishReason) &&
        Objects.equals(nativeResponse, that.nativeResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, model, finishReason);
  }

  @Override
  public String toString() {
    return "AdditionalAttributes{" +
        "id='" + id + '\'' +
        ", model='" + model + '\'' +
        ", finishReason='" + finishReason + '\'' +
        ", nativeResponse='" + nativeResponse + '\'' +
        '}';
  }
}
