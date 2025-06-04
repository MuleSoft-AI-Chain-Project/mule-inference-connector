package com.mulesoft.connectors.inference.internal.connection.provider.zhipuai;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.llmmodels.zhipuai.providers.ZhipuAITextGenerationModelNameProvider;
import com.mulesoft.connectors.inference.internal.connection.types.zhipuai.ZhipuAITextGenerationConnection;
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

@Alias("zhipu-ai")
@DisplayName("ZHIPU_AI")
public class ZhipuAITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(ZhipuAITextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(ZhipuAITextGenerationModelNameProvider.class)
    private String zhipuAIModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public ZhipuAITextGenerationConnection connect() throws ConnectionException {
        logger.debug("ZhipuAITextGenerationConnection connect ...");
        return new ZhipuAITextGenerationConnection(getHttpClient(),getObjectMapper(), zhipuAIModelName,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                textGenerationConnectionParameters.getTimeout());
    }
}