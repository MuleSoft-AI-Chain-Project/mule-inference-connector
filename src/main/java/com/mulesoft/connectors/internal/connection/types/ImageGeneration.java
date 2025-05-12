package com.mulesoft.connectors.internal.connection.types;

import com.mulesoft.connectors.internal.connection.ModerationImageGenerationBase;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.http.api.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ImageGeneration implements ModerationImageGenerationBase {

  private static Logger LOGGER = LoggerFactory.getLogger(ImageGeneration.class);

  private HttpClient httpClient;
  private int timeout;
  private String inferenceType;
  private String apiKey;
  private String modelName;


  public ImageGeneration(
          HttpClient httpClient,
          int timeout,
          String inferenceType,
          String apiKey,
          String modelName

  ) {

    this.httpClient = httpClient;
    this.timeout = timeout;
    this.inferenceType = inferenceType;
    this.apiKey = apiKey;
    this.modelName = modelName;

  }

  public HttpClient getHttpClient() {
    return httpClient;
  }


  public int getTimeout() {
    return timeout;
  }


  public String getInferenceType() {
    return inferenceType;
  }

  public String getApiKey() {
    return apiKey;
  }


  public String getModelName(){
    return modelName;
  }




  public boolean validate() throws ConnectionException {
    return true;
  }




}
