package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.enterprise.event.Observes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;

public class NotificationLifecyleTest extends CdiTestAbstract {

    private ExtensionContext startingEvent;
    private ExtensionContext finishingEvent;
    private ExtensionContext finishedEvent;

    protected void observeStarting(@Observes @TestEvent(EventType.STARTING) ExtensionContext testEvent) {
        this.startingEvent = testEvent;
    }

    protected void observeFinishing(@Observes @TestEvent(EventType.FINISHING) ExtensionContext testEvent) {
        this.finishingEvent = testEvent;
    }

    protected void observeFinished(@Observes @TestEvent(EventType.FINISHED) ExtensionContext testEvent) {
        this.finishedEvent = testEvent;
    }

    @Test
    public void notifyStarting() {
        assertNotNull(startingEvent);
    }

    @Test
    public void notifyFinishing() {
        assertNotNull(finishingEvent);
    }

    @Test
    public void notifyFinished() {
        assertNotNull(finishedEvent);
    }
}
