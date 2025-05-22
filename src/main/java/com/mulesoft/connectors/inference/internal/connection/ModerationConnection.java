package com.mulesoft.connectors.inference.internal.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.service.ModerationService;
import org.mule.runtime.http.api.client.HttpClient;

public class ModerationConnection extends BaseConnection {

    private ModerationService moderationService;

    public ModerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                   String apiKey, String modelName, int timeout,
                                   String apiURL, String inferenceType) {
        super(httpClient,objectMapper,modelName,apiKey,timeout,apiURL,inferenceType);
        this.setBaseService(getModerationService());
    }

    private ModerationService getModerationService() {
        if(moderationService ==null)
            moderationService = new ModerationService(this.getRequestPayloadHelper(),this.getHttpRequestHandler(),
                    this.getResponseHandler(), this.getObjectMapper());
        return moderationService;
    }
}
