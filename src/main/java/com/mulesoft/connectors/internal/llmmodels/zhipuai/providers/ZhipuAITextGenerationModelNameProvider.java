package com.mulesoft.connectors.internal.llmmodels.zhipuai.providers;

import com.mulesoft.connectors.internal.llmmodels.zhipuai.ChatGLMModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class ZhipuAITextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(ChatGLMModelName.values())
            .filter(ChatGLMModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 