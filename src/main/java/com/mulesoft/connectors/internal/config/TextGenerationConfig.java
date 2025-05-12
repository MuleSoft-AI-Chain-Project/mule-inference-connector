package com.mulesoft.connectors.internal.config;

import com.mulesoft.connectors.internal.connection.ai21labs.providers.AI21LabsTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.anthropic.providers.AnthropicTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.azure.providers.AzureAIFoundryTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.azure.providers.AzureOpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.cerebras.providers.CerebrasTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.cohere.providers.CohereTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.databricks.providers.DatabricksTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.deepinfra.providers.DeepInfraTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.deepseek.providers.DeepseekTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.docker.providers.DockerTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.fireworks.providers.FireworksTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.github.providers.GithubTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.gpt4all.providers.GPT4AllTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.groq.providers.GroqTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.huggingface.providers.HuggingFaceTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.ibmwatson.providers.IBMWatsonTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.llamaapi.providers.LlamaAPITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.lmstudio.providers.LMStudioTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.mistralai.providers.MistralAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.nvidia.providers.NvidiaTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.ollama.providers.OllamaTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.openai.providers.OpenAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.openrouter.providers.OpenRouterTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.perplexity.providers.PerplexityTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.portkey.providers.PortkeyTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.together.providers.TogetherTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.vertexai.providers.VertexAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.xai.providers.XAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.xinference.providers.XInferenceTextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.zhipuai.providers.ZhipuAITextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.operations.TextGenerationOperations;
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
        LlamaAPITextGenerationConnectionProvider.class,
        LMStudioTextGenerationConnectionProvider.class,
        MistralAITextGenerationConnectionProvider.class,
        NvidiaTextGenerationConnectionProvider.class,
        OllamaTextGenerationConnectionProvider.class,
        OpenAITextGenerationConnectionProvider.class,
        OpenRouterTextGenerationConnectionProvider.class,
        PerplexityTextGenerationConnectionProvider.class,
        PortkeyTextGenerationConnectionProvider.class,
        TogetherTextGenerationConnectionProvider.class,
        VertexAITextGenerationConnectionProvider.class,
        XAITextGenerationConnectionProvider.class,
        XInferenceTextGenerationConnectionProvider.class,
        ZhipuAITextGenerationConnectionProvider.class
})
@Operations(TextGenerationOperations.class)
public class TextGenerationConfig {
}
