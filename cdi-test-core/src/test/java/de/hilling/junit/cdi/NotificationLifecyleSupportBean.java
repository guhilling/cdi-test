package de.hilling.junit.cdi;

import javax.enterprise.event.Observes;

import org.junit.jupiter.api.extension.ExtensionContext;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class NotificationLifecyleSupportBean {

    protected ExtensionContext startingEvent;
    protected ExtensionContext finishingEvent;
    protected ExtensionContext finishedEvent;

    protected void observeStarting(@Observes @TestEvent(EventType.STARTING) ExtensionContext testEvent) {
        this.startingEvent = testEvent;
    }

    protected void observeFinishing(@Observes @TestEvent(EventType.FINISHING) ExtensionContext testEvent) {
        this.finishingEvent = testEvent;
    }

    protected void observeFinished(@Observes @TestEvent(EventType.FINISHED) ExtensionContext testEvent) {
        this.finishedEvent = testEvent;
    }

}
