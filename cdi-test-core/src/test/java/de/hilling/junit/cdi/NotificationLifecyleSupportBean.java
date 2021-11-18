package de.hilling.junit.cdi;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.jupiter.api.extension.ExtensionContext;

import jakarta.enterprise.event.Observes;

@BypassTestInterceptor
@TestSuiteScoped
public class NotificationLifecyleSupportBean {

    ExtensionContext startingEvent;
    ExtensionContext finishingEvent;
    ExtensionContext finishedEvent;

    protected void observeStarting(@Observes @TestEvent(TestState.STARTING) ExtensionContext testEvent) {
        this.startingEvent = testEvent;
    }

    protected void observeFinishing(@Observes @TestEvent(TestState.FINISHING) ExtensionContext testEvent) {
        this.finishingEvent = testEvent;
    }

    protected void observeFinished(@Observes @TestEvent(TestState.FINISHED) ExtensionContext testEvent) {
        this.finishedEvent = testEvent;
    }

}
