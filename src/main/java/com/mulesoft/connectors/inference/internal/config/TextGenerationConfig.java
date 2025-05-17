package com.mulesoft.connectors.inference.internal.config;

import com.mulesoft.connectors.inference.internal.connection.ai21labs.providers.AI21LabsTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.anthropic.providers.AnthropicTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.azure.providers.AzureAIFoundryTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.azure.providers.AzureOpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.cerebras.providers.CerebrasTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.cohere.providers.CohereTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.databricks.providers.DatabricksTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.deepinfra.providers.DeepInfraTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.deepseek.providers.DeepseekTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.docker.providers.DockerTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.fireworks.providers.FireworksTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.github.providers.GithubTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.gpt4all.providers.GPT4AllTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.groq.providers.GroqTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.huggingface.providers.HuggingFaceTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.ibmwatson.providers.IBMWatsonTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.llmapi.providers.LlmAPITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.lmstudio.providers.LMStudioTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.mistralai.providers.MistralAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.nvidia.providers.NvidiaTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.ollama.providers.OllamaTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.openai.providers.OpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.openaicompatible.providers.OpenAICompatibleTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.openrouter.providers.OpenRouterTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.perplexity.providers.PerplexityTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.portkey.providers.PortkeyTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.together.providers.TogetherTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.providers.VertexAIExpressTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.providers.VertexAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.xai.providers.XAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.xinference.providers.XInferenceTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.zhipuai.providers.ZhipuAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.operations.TextGenerationOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@Configuration(name="text-generation-config")
@ConnectionProviders({
        AI21LabsTextGenerationConnectionProvider.class,
        AnthropicTextGenerationConnectionProvider.class,
        AzureAIFoundryTextGenerationConnectionProvider.class,
        AzureOpenAITextGenerationConnectionProvider.class,
        CerebrasTextGenerationConnectionProvider.class,
        CohereTextGenerationConnectionProvider.class,
        DatabricksTextGenerationConnectionProvider.class,
        DeepInfraTextGenerationConnectionProvider.class,
        DeepseekTextGenerationConnectionProvider.class,
        DockerTextGenerationConnectionProvider.class,
        FireworksTextGenerationConnectionProvider.class,
        GithubTextGenerationConnectionProvider.class,
        GPT4AllTextGenerationConnectionProvider.class,
        GroqTextGenerationConnectionProvider.class,
        HuggingFaceTextGenerationConnectionProvider.class,
        IBMWatsonTextGenerationConnectionProvider.class,
        LlmAPITextGenerationConnectionProvider.class,
        LMStudioTextGenerationConnectionProvider.class,
        MistralAITextGenerationConnectionProvider.class,
        NvidiaTextGenerationConnectionProvider.class,
        OllamaTextGenerationConnectionProvider.class,
        OpenAITextGenerationConnectionProvider.class,
        OpenAICompatibleTextGenerationConnectionProvider.class,
        OpenRouterTextGenerationConnectionProvider.class,
        PerplexityTextGenerationConnectionProvider.class,
        PortkeyTextGenerationConnectionProvider.class,
        TogetherTextGenerationConnectionProvider.class,
        VertexAITextGenerationConnectionProvider.class,
        VertexAIExpressTextGenerationConnectionProvider.class,
        XAITextGenerationConnectionProvider.class,
        XInferenceTextGenerationConnectionProvider.class,
        ZhipuAITextGenerationConnectionProvider.class
})
@Operations(TextGenerationOperations.class)
public class TextGenerationConfig {
}
