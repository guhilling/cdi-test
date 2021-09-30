package de.hilling.junit.cdi.microprofile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

/**
 * Prevent any problems with non-resolved global properties.
 */
@TestSuiteScoped
public class DummyConfigSource implements ConfigSource {

    private final Map<String, String> properties = new HashMap<>();

    public DummyConfigSource() {
        properties.put("dummy", "1");
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "test-config";
    }
}
