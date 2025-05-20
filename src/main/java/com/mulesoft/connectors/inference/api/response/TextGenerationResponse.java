package com.mulesoft.connectors.inference.api.response;

import java.util.Objects;

public class TextGenerationResponse {
    private final String response;

    public TextGenerationResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextGenerationResponse that = (TextGenerationResponse) o;
        return Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(response);
    }

    @Override
    public String toString() {
        return "TextGenerationResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}
