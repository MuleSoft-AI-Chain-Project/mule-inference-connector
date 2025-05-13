package com.mulesoft.connectors.internal.connection;


import org.mule.runtime.http.api.client.HttpClient;

@Deprecated
public interface ModerationImageGenerationBase {
    HttpClient getHttpClient();
    String getInferenceType();
    String getApiKey();
    String getModelName();
    int getTimeout();
}
