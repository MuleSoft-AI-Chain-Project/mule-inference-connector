package com.mulesoft.connectors.inference.api.proxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultProxyConfigTest {

  @Test
  void testEquals() {
    DefaultProxyConfig config1 = createConfig("host1", 8080, "user1", "pass1", "localhost");
    DefaultProxyConfig config2 = createConfig("host1", 8080, "user1", "pass1", "localhost");
    DefaultProxyConfig config3 = createConfig("host2", 8080, "user1", "pass1", "localhost");
    DefaultProxyConfig config4 = createConfig("host1", 9090, "user1", "pass1", "localhost");
    DefaultProxyConfig config5 = createConfig("host1", 8080, "user2", "pass1", "localhost");
    DefaultProxyConfig config6 = createConfig("host1", 8080, "user1", "pass2", "localhost");
    DefaultProxyConfig config7 = createConfig("host1", 8080, "user1", "pass1", "127.0.0.1");
    DefaultProxyConfig configWithNulls = createConfig(null, 0, null, null, null);
    DefaultProxyConfig configWithNulls2 = createConfig(null, 0, null, null, null);
    DefaultProxyConfig configMixed = createConfig("host1", 8080, null, null, null);

    // Covers: this == o (reflexive)
    assertEquals(config1, config1);

    // Covers: Objects.equals comparison - equal objects
    assertEquals(config1, config2);

    // Covers: Objects.equals comparison - different host
    assertNotEquals(config1, config3);

    // Covers: port comparison - different port
    assertNotEquals(config1, config4);

    // Covers: Objects.equals comparison - different username
    assertNotEquals(config1, config5);

    // Covers: Objects.equals comparison - different password
    assertNotEquals(config1, config6);

    // Covers: Objects.equals comparison - different nonProxyHosts
    assertNotEquals(config1, config7);

    // Covers: Objects.equals with null values
    assertEquals(configWithNulls, configWithNulls2);
    assertNotEquals(config1, configWithNulls);
    assertNotEquals(configMixed, config1);

    // Covers: o == null
    assertNotEquals(null, config1);

    // Covers: getClass() != o.getClass()
    assertNotEquals("string", config1);
  }

  @Test
  void testHashCode() {
    DefaultProxyConfig config1 = createConfig("host1", 8080, "user1", "pass1", "localhost");
    DefaultProxyConfig config2 = createConfig("host1", 8080, "user1", "pass1", "localhost");

    // Covers: Objects.hash()
    assertEquals(config1.hashCode(), config2.hashCode());
  }

  private DefaultProxyConfig createConfig(String host, int port, String username, String password, String nonProxyHosts) {
    DefaultProxyConfig config = new DefaultProxyConfig();
    setField(config, "host", host);
    setField(config, "port", port);
    setField(config, "username", username);
    setField(config, "password", password);
    setField(config, "nonProxyHosts", nonProxyHosts);
    return config;
  }

  private void setField(Object obj, String fieldName, Object value) {
    try {
      java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(obj, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
