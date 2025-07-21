package com.mulesoft.connectors.inference.api.proxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultNtlmProxyConfigTest {

    @Test
    void testEquals() {
        DefaultNtlmProxyConfig config1 = createConfig("host1", 8080, "user1", "pass1", "localhost", "domain1");
        DefaultNtlmProxyConfig config2 = createConfig("host1", 8080, "user1", "pass1", "localhost", "domain1");
        DefaultNtlmProxyConfig config3 = createConfig("host1", 8080, "user1", "pass1", "localhost", "domain2");
        DefaultNtlmProxyConfig config4 = createConfig("host2", 8080, "user1", "pass1", "localhost", "domain1");
        DefaultNtlmProxyConfig configWithNulls = createConfig(null, 0, null, null, null, null);
        DefaultNtlmProxyConfig configWithNulls2 = createConfig(null, 0, null, null, null, null);
        DefaultNtlmProxyConfig configMixed = createConfig("host1", 8080, "user1", "pass1", "localhost", null);
        
        // Covers: this == o (reflexive)
        assertEquals(config1, config1);
        
        // Covers: Objects.equals comparison - equal objects
        assertEquals(config1, config2);
        
        // Covers: Objects.equals comparison - different ntlmDomain
        assertNotEquals(config1, config3);
        
        // Covers: super.equals() call - different parent field
        assertNotEquals(config1, config4);
        
        // Covers: Objects.equals with null values
        assertEquals(configWithNulls, configWithNulls2);
        assertNotEquals(config1, configWithNulls);
        assertNotEquals(configMixed, config1);
        
        // Covers: o == null
        assertNotEquals(null, config1);
        
        // Covers: getClass() != o.getClass()
        assertNotEquals("string", config1);
        
        // Covers: super.equals() returns false
        DefaultProxyConfig parentConfig = createParentConfig();
        assertNotEquals(config1, parentConfig);
    }

    @Test
    void testHashCode() {
        DefaultNtlmProxyConfig config1 = createConfig("host1", 8080, "user1", "pass1", "localhost", "domain1");
        DefaultNtlmProxyConfig config2 = createConfig("host1", 8080, "user1", "pass1", "localhost", "domain1");
        
        // Covers: Objects.hash() with super.hashCode()
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    private DefaultNtlmProxyConfig createConfig(String host, int port, String username, String password, 
                                                String nonProxyHosts, String ntlmDomain) {
        DefaultNtlmProxyConfig config = new DefaultNtlmProxyConfig();
        setField(config, "host", host);
        setField(config, "port", port);
        setField(config, "username", username);
        setField(config, "password", password);
        setField(config, "nonProxyHosts", nonProxyHosts);
        setField(config, "ntlmDomain", ntlmDomain);
        return config;
    }

    private DefaultProxyConfig createParentConfig() {
        DefaultProxyConfig config = new DefaultProxyConfig();
        setField(config, "host", "host1");
        setField(config, "port", 8080);
        setField(config, "username", "user1");
        setField(config, "password", "pass1");
        setField(config, "nonProxyHosts", "localhost");
        return config;
    }

    private void setField(Object obj, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = findField(obj.getClass(), fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private java.lang.reflect.Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }
} 