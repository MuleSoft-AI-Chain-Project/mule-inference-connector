package com.mulesoft.connectors.inference.internal.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AnthropicRequestPayloadHelper extends RequestPayloadHelper {

    private static final Logger logger = LoggerFactory.getLogger(AnthropicRequestPayloadHelper.class);

    public AnthropicRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public String buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                       String instructions, String data, InputStream tools) throws IOException {

        List<FunctionDefinitionRecord> toolsRecord = parseInputStreamToTools(tools);

        logger.debug("toolsArray: {}", toolsRecord);

        List<ChatPayloadDTO> messagesArray = createMessagesArrayWithSystemPrompt(
                connection, template + " - " + instructions, data);

        return connection.getObjectMapper()
                .writeValueAsString(buildPayload(connection, messagesArray, toolsRecord));
    }
}
