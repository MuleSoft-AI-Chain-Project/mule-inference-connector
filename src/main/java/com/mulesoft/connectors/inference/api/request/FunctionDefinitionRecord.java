package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;

public record FunctionDefinitionRecord(String type,Function function)implements Serializable{}
