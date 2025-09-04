package com.mulesoft.connectors.inference.api.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class AdditionalAttributesTest {

  @Test
  void testEquals() {
    Map<String, FilterResult> contentFilter1 = Map.of("hate", new FilterResult(false, false, "safe"));
    List<PromptFilterResult> promptFilter1 = List.of(new PromptFilterResult(0, contentFilter1));
    Map<String, FilterResult> contentFilter2 = Map.of("violence", new FilterResult(true, true, "high"));
    List<PromptFilterResult> promptFilter2 = List.of(new PromptFilterResult(1, contentFilter2));

    AdditionalAttributes attributes1 = new AdditionalAttributes("id1", "model1", "stop", contentFilter1, promptFilter1);
    AdditionalAttributes attributes2 = new AdditionalAttributes("id1", "model1", "stop", contentFilter1, promptFilter1);
    AdditionalAttributes attributes3 = new AdditionalAttributes("id2", "model1", "stop", contentFilter1, promptFilter1);
    AdditionalAttributes attributes4 = new AdditionalAttributes("id1", "model2", "stop", contentFilter1, promptFilter1);
    AdditionalAttributes attributes5 = new AdditionalAttributes("id1", "model1", "continue", contentFilter1, promptFilter1);
    AdditionalAttributes attributes6 = new AdditionalAttributes("id1", "model1", "stop", contentFilter2, promptFilter1);
    AdditionalAttributes attributes7 = new AdditionalAttributes("id1", "model1", "stop", contentFilter1, promptFilter2);
    AdditionalAttributes attributesWithNulls = new AdditionalAttributes(null, null, null, null, null);
    AdditionalAttributes attributesWithNulls2 = new AdditionalAttributes(null, null, null, null, null);
    AdditionalAttributes attributesMixed = new AdditionalAttributes("id1", null, "stop", null, promptFilter1);

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

    // Covers: Objects.equals comparison - different contentFilterResults
    assertNotEquals(attributes1, attributes6);

    // Covers: Objects.equals comparison - different promptFilterResults
    assertNotEquals(attributes1, attributes7);

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
    Map<String, FilterResult> contentFilter = Map.of("hate", new FilterResult(false, false, "safe"));
    List<PromptFilterResult> promptFilter = List.of(new PromptFilterResult(0, contentFilter));

    AdditionalAttributes attributes1 = new AdditionalAttributes("id1", "model1", "stop", contentFilter, promptFilter);
    AdditionalAttributes attributes2 = new AdditionalAttributes("id1", "model1", "stop", contentFilter, promptFilter);

    // Covers: Objects.hash()
    assertEquals(attributes1.hashCode(), attributes2.hashCode());
  }

  @Test
  void testToString() {
    Map<String, FilterResult> contentFilter = Map.of("hate", new FilterResult(false, false, "safe"));
    List<PromptFilterResult> promptFilter = List.of(new PromptFilterResult(0, contentFilter));

    AdditionalAttributes attributes = new AdditionalAttributes("id1", "model1", "stop", contentFilter, promptFilter);

    // Covers: toString() method
    String result = attributes.toString();
    assertTrue(result.contains("AdditionalAttributes{"));
    assertTrue(result.contains("id='id1'"));
    assertTrue(result.contains("model='model1'"));
    assertTrue(result.contains("finishReason='stop'"));
    assertTrue(result.contains("contentFilterResults="));
    assertTrue(result.contains("promptFilterResults="));
  }
}
