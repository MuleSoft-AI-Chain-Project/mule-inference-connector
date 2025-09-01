package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.api.request.FunctionSchema;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.ContentRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.FunctionDeclarationsWrapper;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiGenerationConfigRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.GeminiPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.PartRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.SystemInstructionRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.gemini.InlineData;
import com.mulesoft.connectors.inference.internal.dto.vision.gemini.Part;
import com.mulesoft.connectors.inference.internal.dto.vision.gemini.VisionContentRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeminiRequestPayloadHelper extends RequestPayloadHelper {

  private static final Logger logger = LoggerFactory.getLogger(GeminiRequestPayloadHelper.class);

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
    List<ChatPayloadRecord> openAIFormatMessages = objectMapper.readValue(messages,
                                                                          objectMapper.getTypeFactory()
                                                                              .constructCollectionType(List.class,
                                                                                                       ChatPayloadRecord.class));

    // Step 2: Convert OpenAI format to Gemini format
    List<ContentRecord> contentRecords = openAIFormatMessages.stream()
        .map(this::convertToGeminiFormat)
        .toList();

    // Step 3: Build final Gemini payload
    return new GeminiPayloadRecord<>(contentRecords, null, // Optional: systemInstruction if needed
                                     buildGeminiGenerationConfig(connection.getMaxTokens(), connection.getTemperature(),
                                                                 connection.getTopP()),
                                     null, // Optional: safetySettings
                                     null // Optional: tools
    );
  }

  @Override
  public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                   String instructions, String data, InputStream tools)
      throws IOException {

    // STEP 1: Parse to Gemini-compatible function declarations
    List<Function> functionDeclarations = parseInputStreamToFunctionDeclarations(tools);
    logger.debug("functionDeclarations: {}", functionDeclarations);

    // STEP 2: Create System Instruction
    PartRecord partRecord = new PartRecord(template + " - " + instructions, null);

    SystemInstructionRecord systemInstructionRecord = new SystemInstructionRecord(List.of(partRecord));

    // STEP 3: Call Gemini payload builder (must support function_declarations)

    GeminiPayloadRecord<ContentRecord> geminiPayload = buildGeminiPayload(
                                                                          connection,
                                                                          data,
                                                                          Collections.emptyList(), // prompt contents
                                                                          systemInstructionRecord,
                                                                          functionDeclarations // <-- Pass Gemini-compatible
                                                                                               // format
    );
    logger.debug("geminiPayload: {}", geminiPayload);

    return geminiPayload;
  }


  @Override
  public VisionRequestPayloadDTO createRequestImageURL(VisionModelConnection connection, String prompt, String imageUrl)
      throws IOException {

    Object content = getGoogleVisionContentRecord(prompt, imageUrl);

    return buildVisionRequestPayload(connection, List.of(content));
  }

  private List<Function> parseInputStreamToFunctionDeclarations(InputStream inputStream) throws IOException {

    List<FunctionDefinitionRecord> openAIFormatTools = objectMapper.readValue(
                                                                              inputStream,
                                                                              objectMapper.getTypeFactory()
                                                                                  .constructCollectionType(List.class,
                                                                                                           FunctionDefinitionRecord.class));

    return Optional.ofNullable(openAIFormatTools)
        .map(tools -> tools.stream()
            .map(FunctionDefinitionRecord::function)
            .filter(function -> function != null && function.parameters() != null)
            .map(function -> new Function(
                                          function.name(),
                                          function.description(),
                                          mapGeminiCompatibleFunctionSchema(function.parameters())))
            .toList())
        .orElse(Collections.emptyList());
  }

  private VisionContentRecord getGoogleVisionContentRecord(String prompt, String imageUrl) throws IOException {
    List<Part> parts = new ArrayList<>();

    if (isBase64String(imageUrl)) {
      InlineData inlineData = new InlineData(getMimeType(imageUrl), imageUrl);
      parts.add(new Part(inlineData, null, null));
    } else {
      throw new UnsupportedOperationException("Image Read By URI Operation not supported");
    }

    parts.add(new Part(null, null, prompt));

    return new VisionContentRecord("user", parts);
  }

  public VisionRequestPayloadDTO buildVisionRequestPayload(VisionModelConnection connection, List<Object> messagesArray) {

    return new GeminiPayloadRecord<>(messagesArray,
                                     null,
                                     buildGeminiGenerationConfig(connection.getMaxTokens(), connection.getTemperature(),
                                                                 connection.getTopP()),
                                     null,
                                     null);

  }

  private GeminiPayloadRecord<ContentRecord> buildGeminiPayload(TextGenerationConnection connection,
                                                                String prompt,
                                                                List<String> safetySettings,
                                                                SystemInstructionRecord systemInstruction,
                                                                List<Function> functions) {

    PartRecord partRecord = new PartRecord(prompt, null);

    ContentRecord contentRecord = new ContentRecord("user", List.of(partRecord));

    // Convert functions into tools wrapper
    List<FunctionDeclarationsWrapper> tools = Optional.ofNullable(functions)
        .filter(functionList -> !functionList.isEmpty())
        .map(functionList -> List.of(new FunctionDeclarationsWrapper(functionList)))
        .orElse(null);

    return new GeminiPayloadRecord<>(
                                     List.of(contentRecord),
                                     systemInstruction,
                                     buildGeminiGenerationConfig(
                                                                 connection.getMaxTokens(),
                                                                 connection.getTemperature(),
                                                                 connection.getTopP()),
                                     safetySettings != null ? safetySettings : Collections.emptyList(),
                                     tools);
  }

  private GeminiGenerationConfigRecord buildGeminiGenerationConfig(Number maxTokens, Number temperature,
                                                                   Number topP) {
    // create the generationConfig
    return new GeminiGenerationConfigRecord(List.of("TEXT"), temperature,
                                            topP, maxTokens);
  }

  /**
   * Maps FunctionSchema to Gemini-compatible format using only supported OpenAPI schema attributes: - type: The data type
   * (object, string, integer, boolean, array) - description: Clear explanation of the parameter's purpose - properties:
   * Individual parameters for object type - required: Array of mandatory parameter names - enum: Fixed set of allowed values
   * (optional)
   */
  private FunctionSchema mapGeminiCompatibleFunctionSchema(FunctionSchema parameters) {
    return new FunctionSchema(
                              parameters.type(), // type - supported (string, integer, boolean, array, object)
                              parameters.description(), // description - supported
                              parameters.enumValues(), // enum - supported (optional, for fixed value sets)
                              null, // format - not in supported subset
                              null, // examples - not in supported subset
                              null, // defs - not in supported subset
                              null, // allOf - not in supported subset
                              null, // anyOf - not in supported subset
                              null, // oneOf - not in supported subset
                              null, // ref - not in supported subset
                              parameters.properties(), // properties - supported (for object type)
                              parameters.required(), // required - supported (array of mandatory parameters)
                              false, // additionalProperties - explicitly disabled for Gemini
                              null, // minProperties - not in supported subset
                              null, // maxProperties - not in supported subset
                              null, // items - not explicitly mentioned in supported subset
                              null, // minItems - not in supported subset
                              null, // maxItems - not in supported subset
                              null, // uniqueItems - not in supported subset
                              null, // minLength - not in supported subset
                              null, // maxLength - not in supported subset
                              null, // pattern - not in supported subset
                              null, // minimum - not in supported subset
                              null, // maximum - not in supported subset
                              null, // exclusiveMinimum - not in supported subset
                              null, // exclusiveMaximum - not in supported subset
                              null, // constValue - not in supported subset
                              null // defaultValue - not in supported subset
    );
  }

  private ContentRecord convertToGeminiFormat(ChatPayloadRecord msg) {
    String role = "assistant".equals(msg.role()) ? "model" : msg.role();
    PartRecord part = new PartRecord(msg.content(), null);
    return new ContentRecord(role, List.of(part));
  }

}
