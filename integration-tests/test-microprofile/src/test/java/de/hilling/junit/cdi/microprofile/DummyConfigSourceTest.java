package de.hilling.junit.cdi.microprofile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DummyConfigSourceTest {

    DummyConfigSource configSource;

    @BeforeEach
    void setup() {
        configSource = new DummyConfigSource();
    }

    @Test
    void defaultPropertiesEmpty() {
        assertEquals(1, configSource.getProperties().size());
    }

    @Test
    void defaultPropertyDummy() {
        assertEquals("dummy", configSource.getPropertyNames().iterator().next());
    }

    @Test
    void defaultPropertyValue() {
        assertEquals("1", configSource.getValue("dummy"));
    }

    @Test
    void defaultSourceName() {
        assertEquals("test-config", configSource.getName());
    }

}