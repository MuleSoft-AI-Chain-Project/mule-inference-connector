package com.mulesoft.connectors.inference.api.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KeyValueTest {

  static class TestKeyValue extends KeyValue {
  }
  static class OtherKeyValue extends KeyValue {
  }

  @Test
  void testGettersAndSetters() {
    TestKeyValue kv = new TestKeyValue();
    kv.setKey("k1");
    kv.setValue("v1");

    assertEquals("k1", kv.getKey());
    assertEquals("v1", kv.getValue());
  }

  @Test
  void testEquals_sameValues_areEqual() {
    TestKeyValue a = new TestKeyValue();
    TestKeyValue b = new TestKeyValue();

    a.setKey("k");
    a.setValue("v");
    b.setKey("k");
    b.setValue("v");

    assertEquals(a, b);
    assertEquals(b, a);
    assertEquals(a, a); // reflexive
  }

  @Test
  void testEquals_differentKeyOrValue_notEqual() {
    TestKeyValue a = new TestKeyValue();
    TestKeyValue b = new TestKeyValue();
    TestKeyValue c = new TestKeyValue();

    a.setKey("k1");
    a.setValue("v");
    b.setKey("k2");
    b.setValue("v");
    c.setKey("k1");
    c.setValue("v2");

    assertNotEquals(a, b); // different key
    assertNotEquals(a, c); // different value
  }

  @Test
  void testEquals_nullAndDifferentClass() {
    TestKeyValue a = new TestKeyValue();
    a.setKey("k");
    a.setValue("v");

    assertNotEquals(null, a);

    OtherKeyValue other = new OtherKeyValue();
    other.setKey("k");
    other.setValue("v");
    // equals uses getClass(), so different subclass should not be equal
    assertNotEquals(a, other);
  }

  @Test
  void testHashCode_consistentWithEquals() {
    TestKeyValue a = new TestKeyValue();
    TestKeyValue b = new TestKeyValue();
    TestKeyValue c = new TestKeyValue();

    a.setKey("k");
    a.setValue("v");
    b.setKey("k");
    b.setValue("v");
    c.setKey("k");
    c.setValue("v2");

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a, c);
  }
}
