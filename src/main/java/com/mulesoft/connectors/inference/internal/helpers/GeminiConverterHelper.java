package com.mulesoft.connectors.inference.internal.helpers;

import com.mulesoft.connectors.inference.api.request.FunctionSchema;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiParameters;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiProperty;

import java.util.HashMap;
import java.util.Map;

public class GeminiConverterHelper {

  public static GeminiParameters convertToGeminiParameters(FunctionSchema original) {
    Map<String, GeminiProperty> convertedProps = new HashMap<>();
    original.properties().forEach((k, v) -> convertedProps.put(k, new GeminiProperty(v.type(), v.description(), v.enumValues())));

    return new GeminiParameters(
                                original.type(),
                                convertedProps,
                                original.required());
  }
}
