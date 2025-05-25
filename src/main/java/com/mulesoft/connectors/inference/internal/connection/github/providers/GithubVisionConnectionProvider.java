package com.mulesoft.connectors.inference.internal.connection.github.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.github.GithubVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.github.providers.GithubVisionModelNameProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("github-vision")
@DisplayName("GitHub")
public class GithubVisionConnectionProvider extends VisionModelConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(GithubVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(GithubVisionModelNameProvider.class)
    private String gitHubModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public GithubVisionConnection connect() throws ConnectionException {
        logger.debug("GithubVisionConnection connect ...");
        return new GithubVisionConnection(getHttpClient(),getObjectMapper(), gitHubModelName,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(),
                textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(),
                textGenerationConnectionParameters.getTimeout());
    }
}