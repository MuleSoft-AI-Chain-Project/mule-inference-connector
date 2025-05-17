package com.mulesoft.connectors.inference.internal.connection.huggingface.providers;

import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.BaseConnectionProvider;
import com.mulesoft.connectors.inference.internal.llmmodels.huggingface.providers.HuggingFaceImageGenerationModelNameProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
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

@Alias("hugging-face-image")
@DisplayName("Hugging Face")
public class HuggingFaceImageConnectionProvider extends BaseConnectionProvider {

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
  public BaseConnection connect() {
    logger.debug("BaseConnection connect ...");

    return new BaseConnection(getHttpClient(),getObjectMapper(), huggingFaceModelName, baseConnectionParameters.getApiKey(),
            baseConnectionParameters.getTimeout(), getImageGenerationAPIURL(huggingFaceModelName), "HUGGING_FACE");
  }

  @Override
  public void disconnect(BaseConnection textGenerationConnection) {
    logger.debug("HuggingFaceImageConnection disconnected ...");
  }

  @Override
  public ConnectionValidationResult validate(BaseConnection textGenerationConnection) {

    logger.debug("Validating connection... ");
    try {
      //TODO implement proper call to validate connection is valid
      // if (textGenerationConnection.isValid()) {
      return ConnectionValidationResult.success();
     /* } else {
        return ConnectionValidationResult.failure("Failed to validate connection to PGVector", null);
      }*/
    } catch (Exception e) {
      return ConnectionValidationResult.failure("Failed to validate connection to PGVector", e);
    }
  }

  private String getImageGenerationAPIURL(String huggingFaceModelName) {
    String urlStr = HUGGINGFACE_URL + URI_GENERATE_IMAGES;
    urlStr = urlStr
            .replace("{model-name}", huggingFaceModelName);
    return urlStr;
  }
}
