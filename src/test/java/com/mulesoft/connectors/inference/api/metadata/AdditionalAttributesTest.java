package com.mulesoft.connectors.inference.api.metadata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AdditionalAttributesTest {

  @Test
  void testEquals() {
    AdditionalAttributes attributes1 = new AdditionalAttributes("id1", "model1", "stop");
    AdditionalAttributes attributes2 = new AdditionalAttributes("id1", "model1", "stop");
    AdditionalAttributes attributes3 = new AdditionalAttributes("id2", "model1", "stop");
    AdditionalAttributes attributes4 = new AdditionalAttributes("id1", "model2", "stop");
    AdditionalAttributes attributes5 = new AdditionalAttributes("id1", "model1", "continue");
    AdditionalAttributes attributesWithNulls = new AdditionalAttributes(null, null, null);
    AdditionalAttributes attributesWithNulls2 = new AdditionalAttributes(null, null, null);
    AdditionalAttributes attributesMixed = new AdditionalAttributes("id1", null, "stop");

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
    AdditionalAttributes attributes1 = new AdditionalAttributes("id1", "model1", "stop");
    AdditionalAttributes attributes2 = new AdditionalAttributes("id1", "model1", "stop");

    // Covers: Objects.hash()
    assertEquals(attributes1.hashCode(), attributes2.hashCode());
  }

  @Test
  void testToString() {
    AdditionalAttributes attributes = new AdditionalAttributes("id1", "model1", "stop");

    // Covers: toString() method
    String result = attributes.toString();
    assertTrue(result.contains("AdditionalAttributes{"));
    assertTrue(result.contains("id='id1'"));
    assertTrue(result.contains("model='model1'"));
    assertTrue(result.contains("finishReason='stop'"));
  }
}
