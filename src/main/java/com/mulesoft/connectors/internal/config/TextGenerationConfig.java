package com.mulesoft.connectors.internal.config;

import com.mulesoft.connectors.internal.connection.mistralai.MistralAIConnectionProvider;
import com.mulesoft.connectors.internal.connection.openai.OpenAIConnectionProvider;
import com.mulesoft.connectors.internal.connection.openrouter.OpenRouterConnectionProvider;
import com.mulesoft.connectors.internal.operations.TextGenerationOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="text-generation-config")
@ConnectionProviders({OpenAIConnectionProvider.class, MistralAIConnectionProvider.class, OpenRouterConnectionProvider.class})
@Operations(TextGenerationOperations.class)
public class TextGenerationConfig {
}
