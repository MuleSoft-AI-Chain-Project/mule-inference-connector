package com.mulesoft.connectors.internal.config;

import com.mulesoft.connectors.internal.connection.huggingface.providers.HuggingFaceImageConnectionProvider;
import com.mulesoft.connectors.internal.connection.openai.providers.OpenAIImageGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.stabilityai.providers.StabilityAIImageConnectionProvider;
import com.mulesoft.connectors.internal.connection.xai.providers.XAIImageConnectionProvider;
import com.mulesoft.connectors.internal.operations.ImageGenerationModelOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="image-generation-config")
@ConnectionProviders({OpenAIImageGenerationConnectionProvider.class,
        HuggingFaceImageConnectionProvider.class,
        StabilityAIImageConnectionProvider.class,
        XAIImageConnectionProvider.class})
@Operations(ImageGenerationModelOperations.class)
public class ImageGenerationConfig {
}
