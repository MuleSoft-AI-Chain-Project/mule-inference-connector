package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.Objects;

public class AdditionalAttributes implements Serializable {

    private final String id;
    private final String model;
    private final String finish_reason;

    public AdditionalAttributes(String id, String model, String finishReason) {
        this.id = id;
        this.model = model;
        this.finish_reason = finishReason;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalAttributes that = (AdditionalAttributes) o;
        return Objects.equals(id, that.id) && Objects.equals(model, that.model) && Objects.equals(finish_reason, that.finish_reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, finish_reason);
    }

    @Override
    public String toString() {
        return "AdditionalAttributes{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", finish_reason='" + finish_reason + '\'' +
                '}';
    }
}
