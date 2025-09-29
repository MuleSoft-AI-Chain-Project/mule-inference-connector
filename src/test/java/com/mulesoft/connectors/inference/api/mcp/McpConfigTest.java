package com.mulesoft.connectors.inference.api.mcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class McpConfigTest {

  @Test
  void testEquals_sameValues_areEqual() {
    McpConfig a = new McpConfig();
    McpConfig b = new McpConfig();

    // via reflection since there is only a getter
    setMcpClientConfigRef(a, "client-1");
    setMcpClientConfigRef(b, "client-1");

    assertEquals(a, b);
    assertEquals(b, a);
    assertEquals(a, a); // reflexive
  }

  @Test
  void testEquals_differentValues_notEqual() {
    McpConfig a = new McpConfig();
    McpConfig b = new McpConfig();

    setMcpClientConfigRef(a, "client-1");
    setMcpClientConfigRef(b, "client-2");

    assertNotEquals(a, b);
  }

  @Test
  void testEquals_nullAndDifferentClass() {
    McpConfig a = new McpConfig();
    setMcpClientConfigRef(a, "client-1");

    assertNotEquals(null, a);
    assertNotEquals("some-string", a);
  }

  @Test
  void testHashCode_consistentWithEquals() {
    McpConfig a = new McpConfig();
    McpConfig b = new McpConfig();
    McpConfig c = new McpConfig();

    setMcpClientConfigRef(a, "client-1");
    setMcpClientConfigRef(b, "client-1");
    setMcpClientConfigRef(c, "client-2");

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a, c);
  }

  private static void setMcpClientConfigRef(McpConfig target, String value) {
    try {
      var field = McpConfig.class.getDeclaredField("mcpClientConfigRef");
      field.setAccessible(true);
      field.set(target, value);
    } catch (ReflectiveOperationException e) {
      fail("Failed to set mcpClientConfigRef via reflection: " + e.getMessage());
    }
  }
}
