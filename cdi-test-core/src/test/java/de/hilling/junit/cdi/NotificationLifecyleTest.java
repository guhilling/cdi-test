package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

public class NotificationLifecyleTest extends CdiTestAbstract {

    @Inject
    private NotificationLifecyleSupportBean supportBean;

    @Test
    public void notifyStarting() {
        assertNotNull(supportBean.startingEvent);
    }

    @Test
    public void notifyFinishing() {
        assertNotNull(supportBean.finishingEvent);
    }

    @Test
    public void notifyFinished() {
        assertNotNull(supportBean.finishedEvent);
    }
}
