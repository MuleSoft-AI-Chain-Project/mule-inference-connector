package com.mulesoft.connectors.inference.api.response;

import java.io.Serializable;
import java.util.List;

public record TextGenerationResponse(String response, List<ToolCall> tools, List<ToolResult> toolsExecutionReport) implements Serializable {

}
