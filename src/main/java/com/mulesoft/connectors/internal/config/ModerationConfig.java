package com.mulesoft.connectors.internal.config;

import com.mulesoft.connectors.internal.connection.mistralai.providers.MistralAIModerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.openai.providers.OpenAIModerationConnectionProvider;
import com.mulesoft.connectors.internal.operations.ModerationOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;


@Configuration(name="moderation-config")
@ConnectionProviders({OpenAIModerationConnectionProvider.class,
        MistralAIModerationConnectionProvider.class})
@Operations(ModerationOperations.class)
public class ModerationConfig {
}
