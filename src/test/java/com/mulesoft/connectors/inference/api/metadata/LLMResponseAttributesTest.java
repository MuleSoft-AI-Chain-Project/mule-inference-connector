package com.mulesoft.connectors.inference.api.metadata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LLMResponseAttributesTest {

  @Test
  void testEquals() {
    TokenUsage tokenUsage1 = new TokenUsage(10, 20, 30);
    TokenUsage tokenUsage2 = new TokenUsage(15, 25, 40);
    AdditionalAttributes additionalAttrs1 = new AdditionalAttributes("id1", "model1", "stop");
    AdditionalAttributes additionalAttrs2 = new AdditionalAttributes("id2", "model2", "continue");

    LLMResponseAttributes attrs1 = new LLMResponseAttributes(tokenUsage1, additionalAttrs1);
    LLMResponseAttributes attrs2 = new LLMResponseAttributes(tokenUsage1, additionalAttrs1);
    LLMResponseAttributes attrs3 = new LLMResponseAttributes(tokenUsage2, additionalAttrs1);
    LLMResponseAttributes attrs4 = new LLMResponseAttributes(tokenUsage1, additionalAttrs2);
    LLMResponseAttributes attrsWithNulls = new LLMResponseAttributes(null, null);
    LLMResponseAttributes attrsWithNulls2 = new LLMResponseAttributes(null, null);
    LLMResponseAttributes attrsMixed = new LLMResponseAttributes(tokenUsage1, null);

    // Covers: this == o (reflexive)
    assertEquals(attrs1, attrs1);

    // Covers: Objects.equals comparison - equal objects
    assertEquals(attrs1, attrs2);

    // Covers: Objects.equals comparison - different tokenUsage
    assertNotEquals(attrs1, attrs3);

    // Covers: Objects.equals comparison - different additionalAttributes
    assertNotEquals(attrs1, attrs4);

    // Covers: Objects.equals with null values
    assertEquals(attrsWithNulls, attrsWithNulls2);
    assertNotEquals(attrs1, attrsWithNulls);
    assertNotEquals(attrsMixed, attrs1);

    // Covers: o == null
    assertNotEquals(null, attrs1);

    // Covers: getClass() != o.getClass()
    assertNotEquals("string", attrs1);
  }

  @Test
  void testHashCode() {
    TokenUsage tokenUsage = new TokenUsage(10, 20, 30);
    AdditionalAttributes additionalAttrs = new AdditionalAttributes("id1", "model1", "stop");
    LLMResponseAttributes attrs1 = new LLMResponseAttributes(tokenUsage, additionalAttrs);
    LLMResponseAttributes attrs2 = new LLMResponseAttributes(tokenUsage, additionalAttrs);

    // Covers: Objects.hash()
    assertEquals(attrs1.hashCode(), attrs2.hashCode());
  }

  @Test
  void testToString() {
    TokenUsage tokenUsage = new TokenUsage(10, 20, 30);
    AdditionalAttributes additionalAttrs = new AdditionalAttributes("id1", "model1", "stop");
    LLMResponseAttributes attrs = new LLMResponseAttributes(tokenUsage, additionalAttrs);

    // Covers: toString() method
    String result = attrs.toString();
    assertTrue(result.contains("LLMResponseAttributes{"));
    assertTrue(result.contains("tokenUsage="));
    assertTrue(result.contains("additionalAttributes="));
  }
}
