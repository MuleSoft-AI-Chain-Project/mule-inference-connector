package com.mulesoft.connectors.inference.api.response;

import java.io.Serializable;

public record ToolCall(String id,String type,Function function)implements Serializable{}
