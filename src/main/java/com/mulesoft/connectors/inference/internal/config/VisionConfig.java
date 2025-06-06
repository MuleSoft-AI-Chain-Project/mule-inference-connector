package com.mulesoft.connectors.inference.internal.config;

import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.mulesoft.connectors.inference.internal.connection.provider.anthropic.AnthropicVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.azure.AzureAIFoundryVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.github.GithubVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.groq.GroqVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.huggingface.HuggingFaceVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.mistralai.MistralAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.ollama.OllamaVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openai.OpenAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openrouter.OpenRouterVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.portkey.PortkeyVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.vertexai.VertexAIExpressVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.vertexai.VertexAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.xai.XAIVisionConnectionProvider;
import com.mulesoft.connectors.inference.internal.operation.VisionModelOperations;

@Configuration(name = "vision-config")
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
    VertexAIVisionConnectionProvider.class,
    XAIVisionConnectionProvider.class
})
@Operations(VisionModelOperations.class)
public class VisionConfig {
}
