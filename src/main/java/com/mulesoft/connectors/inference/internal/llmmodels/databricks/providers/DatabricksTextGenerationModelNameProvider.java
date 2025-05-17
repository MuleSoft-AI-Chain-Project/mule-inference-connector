package com.mulesoft.connectors.inference.internal.llmmodels.databricks.providers;

import com.mulesoft.connectors.inference.internal.llmmodels.databricks.DatabricksModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class DatabricksTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(DatabricksModelName.values())
            .filter(DatabricksModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 