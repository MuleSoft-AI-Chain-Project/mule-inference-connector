package com.mulesoft.connectors.inference.api.metadata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TokenUsageTest {

    @Test
    void testEquals() {
        TokenUsage usage1 = new TokenUsage(10, 20, 30);
        TokenUsage usage2 = new TokenUsage(10, 20, 30);
        TokenUsage usage3 = new TokenUsage(15, 20, 30);
        TokenUsage usage4 = new TokenUsage(10, 25, 30);
        TokenUsage usage5 = new TokenUsage(10, 20, 35);
        
        // Covers: this == o (reflexive)
        assertEquals(usage1, usage1);
        
        // Covers: equals with all same values
        assertEquals(usage1, usage2);
        
        // Covers: different inputCount
        assertNotEquals(usage1, usage3);
        
        // Covers: different outputCount
        assertNotEquals(usage1, usage4);
        
        // Covers: different totalCount
        assertNotEquals(usage1, usage5);
        
        // Covers: o == null
        assertNotEquals(null, usage1);
        
        // Covers: getClass() != o.getClass()
        assertNotEquals("string", usage1);
    }

    @Test
    void testHashCode() {
        TokenUsage usage1 = new TokenUsage(10, 20, 30);
        TokenUsage usage2 = new TokenUsage(10, 20, 30);
        
        // Covers: Objects.hash()
        assertEquals(usage1.hashCode(), usage2.hashCode());
    }
} 