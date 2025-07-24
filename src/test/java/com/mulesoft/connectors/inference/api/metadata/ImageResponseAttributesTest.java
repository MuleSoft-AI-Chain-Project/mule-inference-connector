package com.mulesoft.connectors.inference.api.metadata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageResponseAttributesTest {

  @Test
  void testEquals() {
    ImageResponseAttributes attrs1 = new ImageResponseAttributes("model1", "prompt1");
    ImageResponseAttributes attrs2 = new ImageResponseAttributes("model1", "prompt1");
    ImageResponseAttributes attrs3 = new ImageResponseAttributes("model2", "prompt1");
    ImageResponseAttributes attrs4 = new ImageResponseAttributes("model1", "prompt2");
    ImageResponseAttributes attrsWithNulls = new ImageResponseAttributes(null, null);
    ImageResponseAttributes attrsWithNulls2 = new ImageResponseAttributes(null, null);
    ImageResponseAttributes attrsMixed = new ImageResponseAttributes("model1", null);

    // Covers: this == o (reflexive)
    assertEquals(attrs1, attrs1);

    // Covers: Objects.equals comparison - equal objects
    assertEquals(attrs1, attrs2);

    // Covers: Objects.equals comparison - different model
    assertNotEquals(attrs1, attrs3);

    // Covers: Objects.equals comparison - different promptUsed
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
    ImageResponseAttributes attrs1 = new ImageResponseAttributes("model1", "prompt1");
    ImageResponseAttributes attrs2 = new ImageResponseAttributes("model1", "prompt1");

    // Covers: Objects.hash()
    assertEquals(attrs1.hashCode(), attrs2.hashCode());
  }

  @Test
  void testToString() {
    ImageResponseAttributes attrs = new ImageResponseAttributes("model1", "prompt1");

    // Covers: toString() method
    String result = attrs.toString();
    assertTrue(result.contains("ImageResponseAttributes{"));
    assertTrue(result.contains("model='model1'"));
    assertTrue(result.contains("promptUsed='prompt1'"));
  }
}
