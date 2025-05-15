package com.mulesoft.connectors.internal.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.DefaultRequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RequestPayloadHelper {

    protected final ObjectMapper objectMapper;

    public RequestPayloadHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {
        return buildPayload(
                connection,
                List.of(
                        new ChatPayloadDTO("user",prompt)));
    }

    public RequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {
       // JSONObject payload = new JSONObject();
        DefaultRequestPayloadDTO requestPayloadDTO = new DefaultRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens());
       // payload.put(InferenceConstants.MESSAGES, messagesArray);

       /* // Different max token parameter names for different providers
        if ("GROQ".equalsIgnoreCase(connection.getInferenceType()) ||
                "OPENAI".equalsIgnoreCase(connection.getInferenceType())) {
            payload.put(InferenceConstants.MAX_COMPLETION_TOKENS, connection.getMaxTokens());
        } else {
            payload.put(InferenceConstants.MAX_TOKENS, connection.getMaxTokens());
        }*/
        // Some models don't support temperature/top_p parameters
        /*String modelName = connection.getModelName();
        payload.put(InferenceConstants.MODEL, modelName);*/

       /* if (!Arrays.asList(NO_TEMPERATURE_MODELS).contains(modelName)) {
            payload.put(InferenceConstants.TEMPERATURE, connection.getTemperature());
            payload.put(InferenceConstants.TOP_P, connection.getTopP());
        }*/
        // Add tools array if provided
       /* if (toolsArray != null && !toolsArray.isEmpty()) {
            payload.put(InferenceConstants.TOOLS, toolsArray);
        }*/
        return requestPayloadDTO;
    }

    /**
     * Parse an input stream to a JSON array
     * @param inputStream the input stream to parse
     * @return the parsed JSON array
     * @throws IOException if an error occurs during parsing
     */
    public List<ChatPayloadDTO> parseInputStreamToJsonArray(InputStream inputStream) throws IOException {

        return objectMapper.readValue(
                inputStream,
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class,ChatPayloadDTO.class));
    }
}
