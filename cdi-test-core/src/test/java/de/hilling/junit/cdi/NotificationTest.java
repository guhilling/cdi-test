package de.hilling.junit.cdi;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import javax.enterprise.event.Observes;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class NotificationTest extends CdiTestAbstract {

    private Map<String, Integer> receivedEvents = new HashMap<>();
    private final String methodKeyA= buildKey("testA");
    private final String methodKeyB= buildKey("testB");

    public void observeTestEvents(@Observes Description testEvent) {
        String key = testEvent.getClassName() + "/" + testEvent.getMethodName();
        if (!receivedEvents.containsKey(key)) {
            receivedEvents.put(key, 1);
        } else {
            Integer oldValue = receivedEvents.get(key);
            receivedEvents.put(key, oldValue + 1);
        }
    }

    @Test
    public void testA() {
        assertThat(receivedEvents.keySet(), hasItem(methodKeyA));
    }

    @Test
    public void testB() {
        assertThat(receivedEvents.keySet(), hasItem(methodKeyA));
        assertThat(receivedEvents.get(methodKeyA), is(3));
        assertThat(receivedEvents.keySet(), hasItem(methodKeyB));
    }

    private String buildKey(String methodName) {
        return NotificationTest.class.getCanonicalName() + "/" + methodName;
    }

}
