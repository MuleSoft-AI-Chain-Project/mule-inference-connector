package com.mulesoft.connectors.inference.internal.connection.provider.huggingface;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.ImageGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.huggingface.HuggingFaceImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.huggingface.providers.HuggingFaceImageGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("hugging-face-image")
@DisplayName("Hugging Face")
public class HuggingFaceImageConnectionProvider extends ImageGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(HuggingFaceImageConnectionProvider.class);

  public static final String HUGGINGFACE_URL = "https://router.huggingface.co/hf-inference";
  public static final String URI_GENERATE_IMAGES = "/models/{model-name}";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(HuggingFaceImageGenerationModelNameProvider.class)
  @Placement(order = 1)
  private String huggingFaceModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public HuggingFaceImageGenerationConnection connect() {
    logger.debug("BaseConnection connect ...");

    return new HuggingFaceImageGenerationConnection(getHttpClient(), getObjectMapper(), huggingFaceModelName,
                                                    baseConnectionParameters.getApiKey(),
                                                    baseConnectionParameters.getTimeout(),
                                                    getImageGenerationAPIURL(huggingFaceModelName));
  }

  private String getImageGenerationAPIURL(String huggingFaceModelName) {
    String urlStr = HUGGINGFACE_URL + URI_GENERATE_IMAGES;
    urlStr = urlStr
        .replace("{model-name}", huggingFaceModelName);
    return urlStr;
  }
}
