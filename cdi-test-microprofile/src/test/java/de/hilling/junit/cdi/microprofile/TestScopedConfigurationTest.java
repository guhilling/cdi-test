package de.hilling.junit.cdi.microprofile;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@ConfigPropertyValue(name = TestScopedConfigurationTest.TEST_PROPERTY, value = TestScopedConfigurationTest.TEST_PROPERTY_VALUE)
class TestScopedConfigurationTest {

    public static final String TEST_PROPERTY = "demoTestProperty";
    public static final String TEST_PROPERTY_VALUE = "Hello, Test!";

    @Inject
    private TestScopedConfiguration configuration;

    @Test
    void accessProperties() {
        assertEquals(TEST_PROPERTY_VALUE, configuration.getProperties().get(TEST_PROPERTY));
    }

    @Test
    void propertyNames() {
        assertEquals(1, configuration.getPropertyNames().size());
    }

    @Test
    void accessProperty() {
        assertEquals(TEST_PROPERTY_VALUE, configuration.getValue(TEST_PROPERTY));
    }

    @Test
    void checkName() {
        assertEquals(TestScopedConfiguration.TEST_CONFIGURATION_NAME, configuration.getName());
    }
}