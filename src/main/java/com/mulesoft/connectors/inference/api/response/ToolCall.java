package com.mulesoft.connectors.inference.api.response;

import java.io.Serializable;

public record ToolCall(String id,Function function,int index)implements Serializable{}
