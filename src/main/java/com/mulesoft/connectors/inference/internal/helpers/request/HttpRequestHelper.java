package com.mulesoft.connectors.inference.internal.helpers.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.ModerationConnection;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.moderation.ModerationRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class HttpRequestHelper {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHelper.class);

    protected final HttpClient httpClient;
    protected final ObjectMapper objectMapper;

    public HttpRequestHelper(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public HttpResponse executeChatRestRequest(TextGenerationConnection connection, String resourceUrl,
                                                      TextGenerationRequestPayloadDTO payload) throws IOException, TimeoutException {
        return executeRestRequest(connection,resourceUrl,connection.getObjectMapper().writeValueAsBytes(payload));
    }

    public HttpResponse executeImageGenerationRestRequest(ImageGenerationConnection connection, String resourceUrl,
                                                                 ImageGenerationRequestPayloadDTO payload) throws IOException, TimeoutException {
        return executeRestRequest(connection,resourceUrl,connection.getObjectMapper().writeValueAsBytes(payload));
    }

    public HttpResponse executeVisionRestRequest(VisionModelConnection connection, String resourceUrl,
                                                 VisionRequestPayloadDTO payload) throws IOException, TimeoutException {
        return executeRestRequest(connection,resourceUrl,connection.getObjectMapper().writeValueAsBytes(payload));
    }

    public HttpResponse executeModerationRestRequest(ModerationConnection connection, String resourceUrl,
                                                     ModerationRequestPayloadRecord payload) throws IOException, TimeoutException {
        return executeRestRequest(connection,resourceUrl,connection.getObjectMapper().writeValueAsBytes(payload));
    }

    private HttpResponse executeRestRequest(BaseConnection connection, String resourceUrl,
                                                  byte[] payloadAsBytes) throws IOException, TimeoutException {

        HttpRequestBuilder requestBuilder = createDefaultRequestBuilder(resourceUrl)
                .headers(new MultiMap<>(connection.getAdditionalHeaders()))
                .queryParams(new MultiMap<>(connection.getQueryParams()))
                .entity(new ByteArrayHttpEntity(payloadAsBytes));

        logger.debug("Sending request to URL: {}", resourceUrl);
        logger.trace("Request headers: {}", requestBuilder.getHeaders());
        logger.trace("Request queryParams: {}", requestBuilder.getQueryParams());

        HttpRequestOptions options = getRequestOptions(connection.getTimeout());
        return httpClient.send(requestBuilder.build(), options);
    }

    private HttpRequestBuilder createDefaultRequestBuilder(String url) {
        return HttpRequest.builder()
                .uri(url)
                .method("POST")
                .headers(getDefaultHeaders());
    }

    private MultiMap<String,String> getDefaultHeaders()
    {
        return new MultiMap<>(Map.of("Content-Type", "application/json",
                "Accept", "application/json"));
    }

    private HttpRequestOptions getRequestOptions( int timeout) {
        return HttpRequestOptions.builder()
                .responseTimeout(timeout)
                .followsRedirect(true)
                .build();
    }
}
