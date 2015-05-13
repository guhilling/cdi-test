package de.hilling.junit.cdi;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import org.junit.Test;
import org.junit.runner.Description;

import javax.enterprise.event.Observes;

import static org.junit.Assert.assertNotNull;

public class NotificationLifecyleTest extends CdiTestAbstract {

    private Description startingEvent;
    private Description finishingEvent;
    private Description finishedEvent;

    protected void observeStarting(@Observes @TestEvent(EventType.STARTING) Description testEvent) {
        this.startingEvent = testEvent;
    }

    protected void observeFinishing(@Observes @TestEvent(EventType.FINISHING) Description testEvent) {
        this.finishingEvent = testEvent;
    }

    protected void observeFinished(@Observes @TestEvent(EventType.FINISHED) Description testEvent) {
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
