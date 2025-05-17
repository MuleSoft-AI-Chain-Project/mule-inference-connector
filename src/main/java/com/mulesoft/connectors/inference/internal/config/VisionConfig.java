package com.mulesoft.connectors.inference.internal.config;

import com.mulesoft.connectors.inference.internal.connection.anthropic.providers.AnthropicVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.azure.providers.AzureAIFoundryVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.github.providers.GithubVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.groq.providers.GroqVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.huggingface.providers.HuggingFaceVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.mistralai.providers.MistralAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.ollama.providers.OllamaVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.openai.providers.OpenAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.openrouter.providers.OpenRouterVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.portkey.providers.PortkeyVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.providers.VertexAIExpressVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.providers.VertexAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.operations.VisionModelOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="vision-config")
@ConnectionProviders({
        AnthropicVisionConnectionProvider.class,
        AzureAIFoundryVisionConnectionProvider.class,
        GithubVisionConnectionProvider.class,
        GroqVisionConnectionProvider.class,
        HuggingFaceVisionConnectionProvider.class,
        MistralAIVisionConnectionProvider.class,
        OllamaVisionConnectionProvider.class,
        OpenAIVisionConnectionProvider.class,
        OpenRouterVisionConnectionProvider.class,
        PortkeyVisionConnectionProvider.class,
        VertexAIExpressVisionConnectionProvider.class,
        VertexAIVisionConnectionProvider.class
})
@Operations(VisionModelOperations.class)
public class VisionConfig {
}
