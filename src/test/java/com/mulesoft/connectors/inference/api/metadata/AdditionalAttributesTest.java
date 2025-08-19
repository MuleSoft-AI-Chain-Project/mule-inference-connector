package com.mulesoft.connectors.inference.api.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.Choice;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere.Message;

class AdditionalAttributesTest {

  @Test
  void testEquals() {
    String nativeResponse = "{}";
    AdditionalAttributes attributes1 = new AdditionalAttributes("id1", "model1", "stop", nativeResponse);
    AdditionalAttributes attributes2 = new AdditionalAttributes("id1", "model1", "stop", nativeResponse);
    AdditionalAttributes attributes3 = new AdditionalAttributes("id2", "model1", "stop", nativeResponse);
    AdditionalAttributes attributes4 = new AdditionalAttributes("id1", "model2", "stop", nativeResponse);
    AdditionalAttributes attributes5 = new AdditionalAttributes("id1", "model1", "continue", nativeResponse);
    AdditionalAttributes attributesWithNulls = new AdditionalAttributes(null, null, null, nativeResponse);
    AdditionalAttributes attributesWithNulls2 = new AdditionalAttributes(null, null, null, nativeResponse);
    AdditionalAttributes attributesMixed = new AdditionalAttributes("id1", null, "stop", nativeResponse);

    // Covers: this == o (reflexive)
    assertEquals(attributes1, attributes1);

    // Covers: Objects.equals comparison - equal objects
    assertEquals(attributes1, attributes2);

    // Covers: Objects.equals comparison - different id
    assertNotEquals(attributes1, attributes3);

    // Covers: Objects.equals comparison - different model
    assertNotEquals(attributes1, attributes4);

    // Covers: Objects.equals comparison - different finishReason
    assertNotEquals(attributes1, attributes5);

    // Covers: Objects.equals with null values
    assertEquals(attributesWithNulls, attributesWithNulls2);
    assertNotEquals(attributes1, attributesWithNulls);
    assertNotEquals(attributesMixed, attributes1);

    // Covers: o == null
    assertNotEquals(null, attributes1);

    // Covers: getClass() != o.getClass()
    assertNotEquals("string", attributes1);
  }

  @Test
  void testHashCode() {
    String nativeResponse = "{}";
    AdditionalAttributes attributes1 = new AdditionalAttributes("id1", "model1", "stop", nativeResponse);
    AdditionalAttributes attributes2 = new AdditionalAttributes("id1", "model1", "stop", nativeResponse);

    // Covers: Objects.hash()
    assertEquals(attributes1.hashCode(), attributes2.hashCode());
  }

  @Test
  void testToString() {
    String nativeResponse = "{}";
    AdditionalAttributes attributes = new AdditionalAttributes("id1", "model1", "stop", nativeResponse);

    // Covers: toString() method
    String result = attributes.toString();
    assertTrue(result.contains("AdditionalAttributes{"));
    assertTrue(result.contains("id='id1'"));
    assertTrue(result.contains("model='model1'"));
    assertTrue(result.contains("finishReason='stop'"));
  }
}
