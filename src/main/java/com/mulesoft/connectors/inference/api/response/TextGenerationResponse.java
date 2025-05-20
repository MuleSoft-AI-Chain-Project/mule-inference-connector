package com.mulesoft.connectors.inference.api.response;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ToolCall;

import java.util.List;
import java.util.Objects;

public class TextGenerationResponse {
    private final String response;
    private final List<ToolCall> tools;

    public TextGenerationResponse(String response, List<ToolCall> tools) {
        this.response = response;
        this.tools = tools;
    }

    public String getResponse() {
        return response;
    }

    public List<ToolCall> getTools() {
        return tools;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextGenerationResponse that = (TextGenerationResponse) o;
        return Objects.equals(response, that.response) && Objects.equals(tools, that.tools);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, tools);
    }

    @Override
    public String toString() {
        return "TextGenerationResponse{" +
                "response='" + response + '\'' +
                ", tools=" + tools +
                '}';
    }
}
