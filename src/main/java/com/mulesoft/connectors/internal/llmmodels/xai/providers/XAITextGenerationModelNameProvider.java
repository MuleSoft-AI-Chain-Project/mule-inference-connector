package com.mulesoft.connectors.internal.llmmodels.xai.providers;

import com.mulesoft.connectors.internal.llmmodels.xai.XAIModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class XAITextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(XAIModelName.values())
            .filter(XAIModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 