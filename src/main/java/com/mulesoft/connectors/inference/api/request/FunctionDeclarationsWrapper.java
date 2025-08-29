package com.mulesoft.connectors.inference.api.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FunctionDeclarationsWrapper(@JsonProperty("function_declarations")List<Function>functionDeclarations){}
