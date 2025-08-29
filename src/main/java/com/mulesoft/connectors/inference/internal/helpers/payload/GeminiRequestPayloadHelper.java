package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.api.request.OpenAITool;
import com.mulesoft.connectors.inference.api.request.Parameters;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.ContentRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.FunctionDeclarationsWrapper;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiFunction;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiGenerationConfigRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.PartRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.SystemInstructionRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.DefaultVisionRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.gemini.InlineData;
import com.mulesoft.connectors.inference.internal.dto.vision.gemini.Part;
import com.mulesoft.connectors.inference.internal.dto.vision.gemini.VisionContentRecord;
import com.mulesoft.connectors.inference.internal.helpers.GeminiConverterHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeminiRequestPayloadHelper extends RequestPayloadHelper {

  private static final Logger logger = LoggerFactory.getLogger(GeminiRequestPayloadHelper.class);

  private static final String DEFAULT_MIME_TYPE = "image/jpeg";

  public GeminiRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationRequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {

    return buildGeminiPayload(
                              connection,
                              prompt,
                              Collections.emptyList(),
                              null,
                              Collections.emptyList());

  }

  @Override
  public TextGenerationRequestPayloadDTO buildPromptTemplatePayload(TextGenerationConnection connection, String template,
                                                                    String instructions, String data) {

    PartRecord partRecord = new PartRecord(template + " - " + instructions, null);

    SystemInstructionRecord systemInstructionRecord = new SystemInstructionRecord(List.of(partRecord));
    return buildGeminiPayload(
                              connection,
                              data,
                              Collections.emptyList(),
                              systemInstructionRecord,
                              Collections.emptyList());

  }

  @Override
  public TextGenerationRequestPayloadDTO parseAndBuildChatCompletionPayload(TextGenerationConnection connection,
                                                                            InputStream messages)
      throws IOException {
    // Step 1: Parse OpenAI-style messages
    List<ChatPayloadRecord>openAIMessages=objectMapper.readValue(messages,objectMapper.getTypeFactory().constructCollectionType(List.class,ChatPayloadRecord.class));

    // Step 2: Convert OpenAI format to Gemini format
    List<ContentRecord>geminiMessages=openAIMessages.stream().map(msg->{String role=switch(msg.role()){case"assistant"->"model"; // Gemini
                                                                                                                                 // expects
                                                                                                                                 // "model"
    default->msg.role(); // Keep "user" as is
    };PartRecord part=new PartRecord(msg.content(),null);return new ContentRecord(role,List.of(part));}).collect(Collectors.toList());

    // Step 3: Build final Gemini payload
    return new GeminiPayloadRecord<>(geminiMessages,null, // Optional: systemInstruction if needed
    buildGeminiGenerationConfig(connection.getMaxTokens(),connection.getTemperature(),connection.getTopP()),null, // Optional:
                                                                                                                  // safetySettings
    null // Optional: tools
    );
  }

  protected List<Function> parseInputStreamToFunctionDeclarations(InputStream inputStream) throws IOException {
    List<OpenAITool> openAITools = objectMapper.readValue(
                                                          inputStream, new TypeReference<List<OpenAITool>>() {});

    if (openAITools == null) {
      return Collections.emptyList();
    }

    return openAITools.stream()
        .map(openAITool -> {
          Function function = openAITool.function();
          Parameters parameters = function.parameters();

          if (function == null || parameters == null) {
            return null;
          }

          return new Function(
                              function.name(),
                              function.description(),
                              new Parameters(
                                             parameters.type(),
                                             parameters.properties(),
                                             parameters.required(),
                                             false)); // ⬅️ This removes 'additionalProperties' from Gemini's JSON payload
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }



  @Override
  public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                   String instructions, String data, InputStream tools)
      throws IOException {

    // STEP 1: Parse Gemini-compatible function declarations
    List<Function> functionDeclarations = parseInputStreamToFunctionDeclarations(tools);
    logger.debug("functionDeclarations: {}", functionDeclarations);

    // STEP 2: Create System Instruction
    PartRecord partRecord = new PartRecord(template + " - " + instructions, null);

    SystemInstructionRecord systemInstructionRecord = new SystemInstructionRecord(List.of(partRecord));

    // STEP 3: Call Gemini payload builder (must support function_declarations)

    GeminiPayloadRecord geminiPayload = buildGeminiPayload(
                                                           connection,
                                                           data,
                                                           Collections.emptyList(), // prompt contents
                                                           systemInstructionRecord,
                                                           functionDeclarations // <-- Pass Gemini-compatible format
    );
    logger.debug("geminiPayload: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(geminiPayload));


    return geminiPayload;
  }


  @Override
  public VisionRequestPayloadDTO createRequestImageURL(VisionModelConnection connection, String prompt, String imageUrl)
      throws IOException {

    Object content = getGoogleVisionContentRecord(prompt, imageUrl);


    return buildVisionRequestPayload(connection, List.of(content));
  }



  private VisionContentRecord getGoogleVisionContentRecord(String prompt, String imageUrl) throws IOException {
    List<Part> parts = new ArrayList<>();

    if (isBase64String(imageUrl)) {
      InlineData inlineData = new InlineData(getMimeType(imageUrl), imageUrl);
      parts.add(new Part(inlineData, null, null));
    } else {
      throw new UnsupportedOperationException("Image Read By URI Operation not supported");
      // FileData fileData = new FileData(getMimeTypeFromUrl(imageUrl), imageUrl);
      // parts.add(new Part(null, fileData, null));
    }

    parts.add(new Part(null, null, prompt));

    return new VisionContentRecord("user", parts);
  }

  private VisionRequestPayloadDTO buildVisionRequestPayload(VisionModelConnection connection, List<Object> messagesArray) {

    return new GeminiPayloadRecord<>(messagesArray,
                                     null,
                                     buildGeminiGenerationConfig(connection.getMaxTokens(), connection.getTemperature(),
                                                                 connection.getTopP()),
                                     null,
                                     null);

  }

  private DefaultRequestPayloadRecord getDefaultRequestPayloadDTO(TextGenerationConnection connection,
                                                                  List<ChatPayloadRecord> chatPayloadRecordList) {
    return new DefaultRequestPayloadRecord(connection.getModelName(),
                                           chatPayloadRecordList,
                                           connection.getMaxTokens(),
                                           connection.getTemperature(),
                                           connection.getTopP(), null);
  }

  private DefaultVisionRequestPayloadRecord getDefaultVisionRequestPayloadDTO(VisionModelConnection connection,
                                                                              List<Object> chatPayloadRecordList) {
    return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                                                 chatPayloadRecordList,
                                                 connection.getMaxTokens(),
                                                 connection.getTemperature(),
                                                 connection.getTopP());
  }

  private GeminiPayloadRecord<ContentRecord> buildGeminiPayload(TextGenerationConnection connection,
                                                                String prompt,
                                                                List<String> safetySettings,
                                                                SystemInstructionRecord systemInstruction,
                                                                List<Function> functions) {

    PartRecord partRecord = new PartRecord(prompt, null);

    ContentRecord contentRecord = new ContentRecord("user", List.of(partRecord));

    // Convert functions into GeminiFunction list (exclude unsupported fields like additionalProperties)
    List<FunctionDeclarationsWrapper> tools = null;
    if (functions != null && !functions.isEmpty()) {
      List<GeminiFunction> geminiFunctions = functions.stream()
          .map(func -> new GeminiFunction(
                                          func.name(),
                                          func.description(),
                                          GeminiConverterHelper.convertToGeminiParameters(func.parameters())))
          .toList();

      FunctionDeclarationsWrapper wrapper = new FunctionDeclarationsWrapper(geminiFunctions);
      tools = List.of(wrapper);
    }

    return new GeminiPayloadRecord<>(
                                     List.of(contentRecord),
                                     systemInstruction,
                                     buildGeminiGenerationConfig(
                                                                 connection.getMaxTokens(),
                                                                 connection.getTemperature(),
                                                                 connection.getTopP()),
                                     safetySettings != null ? safetySettings : Collections.emptyList(),
                                     tools

    );
  }



  private GeminiGenerationConfigRecord buildGeminiGenerationConfig(Number maxTokens, Number temperature,
                                                                   Number topP) {
    // create the generationConfig
    return new GeminiGenerationConfigRecord(List.of("TEXT"), temperature,
                                            topP, maxTokens);
  }

  private String getMimeTypeFromUrl(String imageUrl) {
    if(imageUrl==null||imageUrl.isBlank()){return DEFAULT_MIME_TYPE;}

    String trimmedUrl=imageUrl.trim();int lastDotIndex=trimmedUrl.lastIndexOf('.');

    if(lastDotIndex==-1){return DEFAULT_MIME_TYPE;}

    String extension=trimmedUrl.substring(lastDotIndex).toLowerCase();

    return switch(extension){case".png"->"image/png";case".pdf"->"application/pdf";default->DEFAULT_MIME_TYPE;};
  }

}
