package de.hilling.junit.cdi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(CdiTestJunitExtension.class)
public class NotificationLifecyleTest {

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
