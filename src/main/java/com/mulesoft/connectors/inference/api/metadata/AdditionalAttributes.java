package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdditionalAttributes implements Serializable {

    private final String id;
    private final String model;
    @JsonProperty("finish_reason")
    private final String finishReason;

    public AdditionalAttributes(String id, String model, @JsonProperty("finish_reason") String finishReason) {
        this.id = id;
        this.model = model;
        this.finishReason = finishReason;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalAttributes that = (AdditionalAttributes) o;
        return Objects.equals(id, that.id) && Objects.equals(model, that.model) && Objects.equals(finishReason, that.finishReason);
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
                '}';
    }
}
