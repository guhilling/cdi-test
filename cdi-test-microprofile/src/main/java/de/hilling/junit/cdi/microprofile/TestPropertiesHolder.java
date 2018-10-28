package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.enterprise.event.Observes;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@TestSuiteScoped
public class TestPropertiesHolder {

    private HashMap<String, String> properties = new HashMap<>();


    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    protected void clear(@Observes @TestEvent(EventType.FINISHED) ExtensionContext testEvent) {
        properties.clear();
    }

    public void put(String name, String value) {
        properties.put(name, value);
    }
}
