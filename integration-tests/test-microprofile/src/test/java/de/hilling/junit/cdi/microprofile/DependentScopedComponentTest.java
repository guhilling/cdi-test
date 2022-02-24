package de.hilling.junit.cdi.microprofile;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

/**
 * Showcase for Bug #233.
 */
@ExtendWith(CdiTestJunitExtension.class)
class DependentScopedComponentTest {

    @Inject
    private DependentScopedComponent dependentScopedComponent;

    @Test
    void testDefaultValue() {
        assertEquals("Hello World", dependentScopedComponent.getStringProperty());
    }

    /**
     * Overriding defaults won't work as {@link DependentScopedComponent} doesn't have normal scope.
     */
    @Test
    @ConfigPropertyValue(name = "some.string.property", value = "Hello Test")
    void testTestCaseAnnotatedValue() {
        assertEquals("Hello World", dependentScopedComponent.getStringProperty());
    }
}