package com.mulesoft.connectors.internal.config;

import com.mulesoft.connectors.internal.connection.openai.providers.OpenAIImageConnectionProvider;
import com.mulesoft.connectors.internal.operations.ImageGenerationModelOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="image-generation-config")
@ConnectionProviders({OpenAIImageConnectionProvider.class})
@Operations(ImageGenerationModelOperations.class)
public class ImageGenerationConfig {
}
