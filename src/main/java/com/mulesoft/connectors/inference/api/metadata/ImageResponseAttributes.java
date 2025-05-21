package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.Objects;

public class ImageResponseAttributes implements Serializable {

    private final String model;
    private final String promptUsed;

    public ImageResponseAttributes(String model, String promptUsed) {
        this.model = model;
        this.promptUsed = promptUsed;
    }

    public String getModel() {
        return model;
    }

    public String getPromptUsed() {
        return promptUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageResponseAttributes that = (ImageResponseAttributes) o;
        return Objects.equals(model, that.model) && Objects.equals(promptUsed, that.promptUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, promptUsed);
    }

    @Override
    public String toString() {
        return "ImageResponseAttributes{" +
                "model='" + model + '\'' +
                ", promptUsed='" + promptUsed + '\'' +
                '}';
    }
}
