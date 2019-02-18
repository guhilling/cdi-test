package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.enterprise.event.Observes;

@BypassTestInterceptor
@TestSuiteScoped
public class TestInformation {

    private Class<?> activeTest;

    protected synchronized void starting(@Observes @TestEvent(TestState.STARTING) ExtensionContext testContext) {
        activeTest = testContext.getRequiredTestClass();
    }

    protected synchronized void finished(@Observes @TestEvent(TestState.FINISHED) ExtensionContext testContext) {
        activeTest = null;
    }

    public Class<?> getActiveTest() {
        return activeTest;
    }
}
