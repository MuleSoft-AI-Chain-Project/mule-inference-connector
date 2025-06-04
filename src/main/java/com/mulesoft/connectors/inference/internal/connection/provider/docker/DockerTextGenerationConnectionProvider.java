package com.mulesoft.connectors.inference.internal.connection.provider.docker;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.llmmodels.docker.providers.DockerTextGenerationModelNameProvider;
import com.mulesoft.connectors.inference.internal.connection.types.docker.DockerTextGenerationConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("docker")
@DisplayName("Docker")
public class DockerTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(DockerTextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(DockerTextGenerationModelNameProvider.class)
    private String dockerModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = InferenceConstants.DOCKER_MODEL_URL)
    @DisplayName("[Docker Models] Base URL")
    private String dockerModelUrl;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public DockerTextGenerationConnection connect() throws ConnectionException {
        logger.debug("DockerTextGenerationConnection connect ...");
        return new DockerTextGenerationConnection(getHttpClient(),getObjectMapper(), dockerModelName, dockerModelUrl,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                textGenerationConnectionParameters.getTimeout());
    }
}