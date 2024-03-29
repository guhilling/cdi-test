package de.hilling.junit.cdi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test and demo lifecycle events.
 */
@ExtendWith(CdiTestJunitExtension.class)
class NotificationLifecyleTest {

    @Inject
    private NotificationLifecyleSupportBean supportBean;

    @Test
    void notifyStarting() {
        assertNotNull(supportBean.startingEvent);
    }

    @Test
    void notifyFinishing() {
        assertNotNull(supportBean.finishingEvent);
    }

    @Test
    void notifyFinished() {
        assertNotNull(supportBean.finishedEvent);
    }
}
