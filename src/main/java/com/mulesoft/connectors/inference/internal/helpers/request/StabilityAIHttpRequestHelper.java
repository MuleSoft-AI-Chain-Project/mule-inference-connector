package com.mulesoft.connectors.inference.internal.helpers.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.DefaultImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.entity.multipart.HttpPart;
import org.mule.runtime.http.api.domain.entity.multipart.MultipartHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class StabilityAIHttpRequestHelper extends HttpRequestHelper {

    private static final Logger logger = LoggerFactory.getLogger(StabilityAIHttpRequestHelper.class);

    public StabilityAIHttpRequestHelper(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient,objectMapper);
    }

    @Override
    public HttpResponse executeImageGenerationRestRequest(ImageGenerationConnection connection, String resourceUrl,
                                                                 ImageGenerationRequestPayloadDTO payload) throws IOException, TimeoutException {

        DefaultImageRequestPayloadRecord payloadRecord = (DefaultImageRequestPayloadRecord) payload;
        return executeRestRequest(connection,resourceUrl,payloadRecord.prompt());
    }

    private HttpResponse executeRestRequest(BaseConnection connection, String resourceUrl,
                                                  String payload) throws IOException, TimeoutException {

        byte[] promptBytes = payload.getBytes(StandardCharsets.UTF_8);
        HttpEntity entity = new MultipartHttpEntity(
                List.of(
                        new HttpPart("prompt", promptBytes, "text/plain", promptBytes.length)));

        HttpRequestBuilder requestBuilder = createDefaultRequestBuilder(resourceUrl)
                .addHeader("Content-Type", "multipart/form-data")
                .headers(new MultiMap<>(connection.getAdditionalHeaders()))
                .queryParams(new MultiMap<>(connection.getQueryParams()))
                .entity(entity);

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
        // Remove Content-Type to avoid conflicts
        return new MultiMap<>(Map.of("Accept", "application/json"));
    }

    private HttpRequestOptions getRequestOptions( int timeout) {
        return HttpRequestOptions.builder()
                .responseTimeout(timeout)
                .followsRedirect(true)
                .build();
    }
}
