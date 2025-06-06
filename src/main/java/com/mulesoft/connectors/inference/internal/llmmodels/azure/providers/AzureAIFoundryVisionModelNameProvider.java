/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.inference.internal.llmmodels.azure.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.azure.AzureAIFoundryModelName;

import java.util.Arrays;
import java.util.Set;

public class AzureAIFoundryVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(AzureAIFoundryModelName.values())
        .filter(AzureAIFoundryModelName::isVisionSupport).map(String::valueOf));
  }
}
