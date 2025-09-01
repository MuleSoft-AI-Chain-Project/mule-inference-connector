package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import com.mulesoft.connectors.inference.api.request.Function;

import java.io.Serializable;
import java.util.List;

public record FunctionDeclarationsWrapper(

List<Function>functionDeclarations

)implements Serializable{}
