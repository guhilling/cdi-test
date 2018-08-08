package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.enterprise.event.Observes;

import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;

public class NotificationLifecyleTest extends CdiTestAbstract {

    private Object startingEvent;
    private Object finishingEvent;
    private Object finishedEvent;

    protected void observeStarting(@Observes @TestEvent(EventType.STARTING) Object testEvent) {
        this.startingEvent = testEvent;
    }

    protected void observeFinishing(@Observes @TestEvent(EventType.FINISHING) Object testEvent) {
        this.finishingEvent = testEvent;
    }

    protected void observeFinished(@Observes @TestEvent(EventType.FINISHED) Object testEvent) {
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
