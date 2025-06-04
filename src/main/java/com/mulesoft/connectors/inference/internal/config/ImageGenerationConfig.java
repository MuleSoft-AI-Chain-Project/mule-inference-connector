package com.mulesoft.connectors.inference.internal.config;

import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.mulesoft.connectors.inference.internal.connection.provider.huggingface.HuggingFaceImageConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openai.OpenAIImageGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.stabilityai.StabilityAIImageConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.xai.XAIImageConnectionProvider;
import com.mulesoft.connectors.inference.internal.operation.ImageGenerationModelOperations;

@Configuration(name = "image-generation-config")
@ConnectionProviders({OpenAIImageGenerationConnectionProvider.class,
    HuggingFaceImageConnectionProvider.class,
    StabilityAIImageConnectionProvider.class,
    XAIImageConnectionProvider.class})
@Operations(ImageGenerationModelOperations.class)
public class ImageGenerationConfig {
}
