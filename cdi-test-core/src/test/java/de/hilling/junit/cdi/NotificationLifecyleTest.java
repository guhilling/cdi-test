package de.hilling.junit.cdi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.hilling.junit.cdi.junit.CdiTestJunitExtension;

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
