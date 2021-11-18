package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestScoped;

import io.smallrye.config.PropertiesConfigSourceProvider;
import io.smallrye.config.SmallRyeConfigBuilder;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Priority(10000)
@TestScoped
public class TestScopedConfiguration implements ConfigSource {
    public static final String TEST_CONFIGURATION_NAME = "TEST_CASE_MICROPROFILE_CONFIGURATION";

    private static final String META_INF_MICROPROFILE_CONFIG_PROPERTIES = "META-INF/microprofile-config.properties";
    private static final String WEB_INF_MICROPROFILE_CONFIG_PROPERTIES  = "WEB-INF/classes/META-INF/microprofile-config.properties";

    @GlobalTestImplementation
    @Dependent
    @Produces
    private Config testConfig;

    @Inject
    private TestPropertiesHolder properties;

    private Map<String, String> testProperties;

    @PostConstruct
    protected void create() {
        testProperties = properties.getProperties();

        final ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();

        ArrayList<ConfigSource> defaultSources = new ArrayList<>();
        defaultSources.addAll(new PropertiesConfigSourceProvider(META_INF_MICROPROFILE_CONFIG_PROPERTIES, true, classLoader).getConfigSources(
                classLoader));
        defaultSources.addAll(new PropertiesConfigSourceProvider(WEB_INF_MICROPROFILE_CONFIG_PROPERTIES, true, classLoader).getConfigSources(
                classLoader));
        defaultSources.add(this);

        testConfig = new SmallRyeConfigBuilder().addDefaultSources()
                .withSources(defaultSources.toArray(new ConfigSource[0]))
                .addDiscoveredConverters()
                .build();
    }

    @Override
    public Map<String, String> getProperties() {
        return testProperties;
    }

    @Override
    public Set<String> getPropertyNames() {
        return testProperties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return testProperties.get(propertyName);
    }

    @Override
    public String getName() {
        return TEST_CONFIGURATION_NAME;
    }

}
