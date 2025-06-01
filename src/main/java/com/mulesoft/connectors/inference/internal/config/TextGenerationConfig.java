package com.mulesoft.connectors.inference.internal.config;

import com.mulesoft.connectors.inference.internal.connection.provider.ai21labs.AI21LabsTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.anthropic.AnthropicTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.azure.AzureAIFoundryTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.azure.AzureOpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.cerebras.CerebrasTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.cohere.CohereTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.databricks.DatabricksTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.deepinfra.DeepInfraTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.deepseek.DeepseekTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.docker.DockerTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.fireworks.FireworksTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.github.GithubTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.gpt4all.GPT4AllTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.groq.GroqTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.huggingface.HuggingFaceTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.ibmwatson.IBMWatsonTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.llmapi.LlmAPITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.lmstudio.LMStudioTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.mistralai.MistralAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.nvidia.NvidiaTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.ollama.OllamaTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openai.OpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openaicompatible.OpenAICompatibleTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openrouter.OpenRouterTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.perplexity.PerplexityTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.portkey.PortkeyTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.together.TogetherTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.vertexai.VertexAIExpressTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.vertexai.VertexAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.xai.XAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.xinference.XInferenceTextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.zhipuai.ZhipuAITextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.operation.TextGenerationOperations;
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
