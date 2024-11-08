package com.mulesoft.connectors.internal.operations;

import static com.mulesoft.connectors.internal.helpers.ResponseHelper.createLLMResponse;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import com.mulesoft.connectors.internal.helpers.TokenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mulesoft.connectors.internal.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.internal.api.metadata.TokenUsage;
import com.mulesoft.connectors.internal.config.InferenceConfiguration;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Content;

import static org.apache.commons.io.IOUtils.toInputStream;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class InferenceOperations {
  private static final Logger LOGGER = LoggerFactory.getLogger(InferenceOperations.class);


/**
 * Chat completions by messages array including system, users messages i.e. conversation history
 * @throws Exception 
*/
@MediaType(value = APPLICATION_JSON, strict = false)
@Alias("Chat-completions")
@OutputJsonType(schema = "api/response/Response.json")
public org.mule.runtime.extension.api.runtime.operation.Result<InputStream, LLMResponseAttributes> chatCompletion(
                            @Config InferenceConfiguration configuration, 
                            @Content InputStream messages) throws Exception {
   try {
      JSONArray messagesArray = getInputString(messages);
      URL chatCompUrl = getConnectionURLChatCompletion(configuration);

      JSONObject payload = getPayload(configuration, messagesArray, null);

      String response = executeREST(chatCompUrl,configuration, payload.toString());

      JSONObject root = new JSONObject(response);
      String model = root.getString("model");      
      String id = !"OLLAMA".equals(configuration.getInferenceType()) ? root.getString("id") : null;
      JSONObject message;
      String finishReason;

      if (!"OLLAMA".equals(configuration.getInferenceType())) {
        JSONArray choicesArray = root.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        finishReason = firstChoice.getString("finish_reason");
        message = firstChoice.getJSONObject("message");

      } else {
        message = root.getJSONObject("message");
        finishReason = root.getString("done_reason");
      }

      String content = message.getString("content");


      TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(InferenceConstants.RESPONSE, content);
      Map<String, String> responseAttributes = new HashMap<>();;
      responseAttributes.put(InferenceConstants.FINISH_REASON, finishReason); 
      responseAttributes.put(InferenceConstants.MODEL, model); 
      responseAttributes.put(InferenceConstants.ID_STRING, id); 

      LOGGER.debug("Chat completions result {}", response);

      return createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
     } catch (Exception e) {
      //throw new ModuleException("Unable to perform toxicity detection", MuleChainErrorType.AI_SERVICES_FAILURE, e);
      LOGGER.debug("Error in chat completions {}", e.getMessage());
      System.out.println(e.getMessage());
      return null;

    }
  }

/**
 * Simple chat answer prompt
 * @throws Exception 
*/
@MediaType(value = APPLICATION_JSON, strict = false)
@Alias("Chat-answer-prompt")
@OutputJsonType(schema = "api/response/Response.json")
public org.mule.runtime.extension.api.runtime.operation.Result<InputStream, LLMResponseAttributes> chatAnswerPrompt(
                            @Config InferenceConfiguration configuration,
                            @Content String prompt) throws Exception {
   try {
      JSONArray messagesArray = new JSONArray();
      JSONObject usersPrompt = new JSONObject();
      usersPrompt.put("role", "user");
      usersPrompt.put("content", prompt);
      messagesArray.put(usersPrompt);

      URL chatCompUrl = getConnectionURLChatCompletion(configuration);

      JSONObject payload = getPayload(configuration, messagesArray, null);

      String response = executeREST(chatCompUrl,configuration, payload.toString());


      JSONObject root = new JSONObject(response);
      String model = root.getString("model");      
      String id = !"OLLAMA".equals(configuration.getInferenceType()) ? root.getString("id") : null;
      JSONObject message;
      String finishReason;

      if (!"OLLAMA".equals(configuration.getInferenceType())) {
        JSONArray choicesArray = root.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        finishReason = firstChoice.getString("finish_reason");
        message = firstChoice.getJSONObject("message");

      } else {
        message = root.getJSONObject("message");
        finishReason = root.getString("done_reason");
      }

      String content = message.getString("content");

      TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(InferenceConstants.RESPONSE, content);
      Map<String, String> responseAttributes = new HashMap<>();;
      responseAttributes.put(InferenceConstants.FINISH_REASON, finishReason); 
      responseAttributes.put(InferenceConstants.MODEL, model); 
      responseAttributes.put(InferenceConstants.ID_STRING, id); 

      LOGGER.debug("Chat answer prompt result {}", response);

      return createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
     } catch (Exception e) {
      //throw new ModuleException("Unable to perform toxicity detection", MuleChainErrorType.AI_SERVICES_FAILURE, e);
      LOGGER.debug("Error in chat answer prompt {}", e.getMessage());

      return null;

    }
  }

/**
 * Define a prompt template
 * @throws Exception 
*/
@MediaType(value = APPLICATION_JSON, strict = false)
@Alias("Agent-define-prompt-template")
@OutputJsonType(schema = "api/response/Response.json")
public org.mule.runtime.extension.api.runtime.operation.Result<InputStream, LLMResponseAttributes> promptTemplate(
                            @Config InferenceConfiguration configuration,
                            @Content String template, @Content String instructions, 
                            @Content(primary = true) String data ) 
                            throws Exception {
   try {
      JSONArray messagesArray = new JSONArray();
      JSONObject systemMessage = new JSONObject();
      systemMessage.put("role", "system");
      systemMessage.put("content", template + " - " + instructions);
      messagesArray.put(systemMessage);

      JSONObject usersPrompt = new JSONObject();
      usersPrompt.put("role", "user");
      usersPrompt.put("content", data);
      messagesArray.put(usersPrompt);


      URL chatCompUrl = getConnectionURLChatCompletion(configuration);

      JSONObject payload = getPayload(configuration, messagesArray, null);

      String response = executeREST(chatCompUrl,configuration, payload.toString());


      JSONObject root = new JSONObject(response);
      String model = root.getString("model");      
      String id = !"OLLAMA".equals(configuration.getInferenceType()) ? root.getString("id") : null;
      JSONObject message;
      String finishReason;

      if (!"OLLAMA".equals(configuration.getInferenceType())) {
        JSONArray choicesArray = root.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        finishReason = firstChoice.getString("finish_reason");
        message = firstChoice.getJSONObject("message");

      } else {
        message = root.getJSONObject("message");
        finishReason = root.getString("done_reason");
      }

      String content = message.getString("content");

      TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(InferenceConstants.RESPONSE, content);
      Map<String, String> responseAttributes = new HashMap<>();;
      responseAttributes.put(InferenceConstants.FINISH_REASON, finishReason); 
      responseAttributes.put(InferenceConstants.MODEL, model); 
      responseAttributes.put(InferenceConstants.ID_STRING, id); 

      LOGGER.debug("Agent define prompt template result {}", response);

      return createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
     } catch (Exception e) {
      //throw new ModuleException("Unable to perform toxicity detection", MuleChainErrorType.AI_SERVICES_FAILURE, e);
      LOGGER.debug("Error in Agent define prompt template {}", e.getMessage());

      return null;

    }
  }

  /**
 * Define a tools template
 * @throws Exception 
*/
@MediaType(value = APPLICATION_JSON, strict = false)
@Alias("Tools-native-template")
@OutputJsonType(schema = "api/response/Response.json")
public org.mule.runtime.extension.api.runtime.operation.Result<InputStream, LLMResponseAttributes> toolsTemplate(
                            @Config InferenceConfiguration configuration,
                            @Content String template, @Content String instructions, 
                            @Content(primary = true) String data, @Content InputStream tools) 
                            throws Exception {
   try {
      JSONArray toolsArray = getInputString(tools);

      JSONArray messagesArray = new JSONArray();
      JSONObject systemMessage = new JSONObject();
      systemMessage.put("role", "system");
      systemMessage.put("content", template + " - " + instructions);
      messagesArray.put(systemMessage);

      JSONObject usersPrompt = new JSONObject();
      usersPrompt.put("role", "user");
      usersPrompt.put("content", data);
      messagesArray.put(usersPrompt);

      URL chatCompUrl = getConnectionURLChatCompletion(configuration);
      JSONObject payload = getPayload(configuration, messagesArray, toolsArray);
      String response = executeREST(chatCompUrl,configuration, payload.toString());

      JSONObject root = new JSONObject(response);
      String model = root.getString("model");      
      String id = !"OLLAMA".equals(configuration.getInferenceType()) ? root.getString("id") : null;
      JSONObject message;
      String finishReason;

      if (!"OLLAMA".equals(configuration.getInferenceType())) {
        JSONArray choicesArray = root.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        finishReason = firstChoice.getString("finish_reason");
        message = firstChoice.getJSONObject("message");

      } else {
        message = root.getJSONObject("message");
        finishReason = root.getString("done_reason");
      }

      String content = message.has("content") && !message.isNull("content") ? message.getString("content") : null;
      JSONArray tool_calls = message.has("tool_calls") ? message.getJSONArray("tool_calls") : null;

      TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(InferenceConstants.RESPONSE, content);
      jsonObject.put(InferenceConstants.TOOLS, tool_calls);
      Map<String, String> responseAttributes = new HashMap<>();;
      responseAttributes.put(InferenceConstants.FINISH_REASON, finishReason); 
      responseAttributes.put(InferenceConstants.MODEL, model); 
      responseAttributes.put(InferenceConstants.ID_STRING, id); 

      LOGGER.debug("Agent define prompt template result {}", response);

      return createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
     } catch (Exception e) {
      //throw new ModuleException("Unable to perform toxicity detection", MuleChainErrorType.AI_SERVICES_FAILURE, e);
      LOGGER.debug("Error in Agent define prompt template {}", e.getMessage());

      return null;

    }
  }


  private static HttpURLConnection getConnectionObject(URL url, InferenceConfiguration configuration) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
    conn.setRequestProperty("User-Agent", "Mozilla/5.0");  
    conn.setRequestProperty("Accept", "application/json");  
    switch (configuration.getInferenceType()) {
        case "PORTKEY":
            conn.setRequestProperty("x-portkey-api-key", configuration.getApiKey());
            conn.setRequestProperty("x-portkey-virtual-key", configuration.getVirtualKey());
        default:
            conn.setRequestProperty("Authorization", "Bearer " + configuration.getApiKey());;
    
    }

    return conn;
  }

  private static URL getConnectionURLChatCompletion(InferenceConfiguration configuration) throws MalformedURLException {
    switch (configuration.getInferenceType()) {
        case "PORTKEY":
            return new URL(InferenceConstants.PORTKEY_URL + InferenceConstants.CHAT_COMPLETIONS);
        case "GROQ":
            return new URL(InferenceConstants.GROQ_URL + InferenceConstants.CHAT_COMPLETIONS);
        case "HUGGING_FACE": 
            return new URL(InferenceConstants.HUGGINGFACE_URL + "/models/" + configuration.getModelName() + "/v1" + InferenceConstants.CHAT_COMPLETIONS);
        case "OPENROUTER": 
            return new URL(InferenceConstants.OPENROUTER_URL + InferenceConstants.CHAT_COMPLETIONS);
        case "GITHUB": 
            return new URL(InferenceConstants.GITHUB_MODELS_URL + InferenceConstants.CHAT_COMPLETIONS);
        case "OLLAMA": 
            return new URL(configuration.getOllamaUrl() + InferenceConstants.CHAT_COMPLETIONS_OLLAMA);
        default:
            return new URL ("");
        }
  }

  private static JSONObject getPayload(InferenceConfiguration configuration, JSONArray messagesArray, JSONArray toolsArray){
    JSONObject payload = new JSONObject();
    payload.put(InferenceConstants.MODEL, configuration.getModelName());
    payload.put(InferenceConstants.MESSAGES, messagesArray);
    payload.put(InferenceConstants.MAX_TOKENS, configuration.getMaxTokens());
    payload.put(InferenceConstants.TEMPERATURE, configuration.getTemperature());
    payload.put(InferenceConstants.TOP_P, configuration.getTopP());
    payload.put(InferenceConstants.TOOLS, toolsArray != null ? toolsArray : null);
    payload.put("stream", "OLLAMA".equals(configuration.getInferenceType()) ? false : null);

    return payload;

  }

  private static JSONArray getInputString(InputStream inputString) throws IOException {
    InputStreamReader reader = new InputStreamReader(inputString);
    StringBuilder inputStringBuilder = new StringBuilder();
    int c;
    while ((c = reader.read()) != -1) {
        inputStringBuilder.append((char) c);
    }
    return new JSONArray(inputStringBuilder.toString());

  }

  private static String executeREST(URL ressourceUrl, InferenceConfiguration configuration, String payload) {

    try {
        URL url = ressourceUrl;

        HttpURLConnection conn = getConnectionObject(url, configuration);


        // Send the payload
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = payload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();  // Ensure data is sent
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

          try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {

            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine.trim());
                }
            } catch (IOException ex) {
                LOGGER.debug("Error reading error stream {}", ex.getMessage());

            }
            return "Error: " + responseCode;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "Exception occurred: " + e.getMessage();
    }
}


}
