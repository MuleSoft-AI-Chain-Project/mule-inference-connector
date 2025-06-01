package com.mulesoft.connectors.inference.internal.config;

import com.mulesoft.connectors.inference.internal.connection.provider.mistralai.MistralAIModerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.provider.openai.OpenAIModerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.operation.ModerationOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="moderation-config")
@ConnectionProviders({OpenAIModerationConnectionProvider.class,
        MistralAIModerationConnectionProvider.class})
@Operations(ModerationOperations.class)
public class ModerationConfig {
}
