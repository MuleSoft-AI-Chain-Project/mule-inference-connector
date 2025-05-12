package com.mulesoft.connectors.internal.config;

import com.mulesoft.connectors.internal.connection.mistralai.providers.MistralAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.openai.providers.OpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.openrouter.providers.OpenRouterTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.operations.TextGenerationOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="text-generation-config")
@ConnectionProviders({OpenAITextGenerationConnectionProvider.class,
        MistralAITextGenerationConnectionProvider.class,
        OpenRouterTextGenerationConnectionProvider.class})
@Operations(TextGenerationOperations.class)
public class TextGenerationConfig {
}
