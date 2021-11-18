package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.jupiter.api.extension.ExtensionContext;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.Arrays;

@TestSuiteScoped
@BypassTestInterceptor
public class TestConfigListener {

    @Inject
    private TestPropertiesHolder testProperties;

    protected void observeStarting(@Observes @TestEvent(TestState.STARTED) ExtensionContext testEvent) {
        testEvent.getTestClass()
                 .ifPresent(testClass -> Arrays.stream(testClass.getAnnotationsByType(ConfigPropertyValue.class))
                                               .forEach(this::applyPropertyValue));
        testEvent.getTestMethod()
                 .ifPresent(testMethod -> Arrays.stream(testMethod.getAnnotationsByType(ConfigPropertyValue.class))
                                                .forEach(this::applyPropertyValue));
    }

    private void applyPropertyValue(ConfigPropertyValue configPropertyValue) {
        testProperties.put(configPropertyValue.name(), configPropertyValue.value());
    }

}
