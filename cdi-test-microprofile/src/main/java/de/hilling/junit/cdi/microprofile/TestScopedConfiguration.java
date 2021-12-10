package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.ContextControlWrapper;
import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestScoped;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import io.smallrye.config.PropertiesConfigSourceProvider;
import io.smallrye.config.SmallRyeConfigBuilder;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

public class TestScopedConfiguration implements ConfigSource {
    public static final String TEST_CONFIGURATION_NAME = "TEST_CASE_MICROPROFILE_CONFIGURATION";

    private static final String META_INF_MICROPROFILE_CONFIG_PROPERTIES = "META-INF/microprofile-config.properties";
    private static final String WEB_INF_MICROPROFILE_CONFIG_PROPERTIES  = "WEB-INF/classes/META-INF/microprofile-config.properties";
    public static final int PRIORITY = 10000;

    private TestPropertiesHolder propertiesHolder;

    private void init() {
        if(propertiesHolder == null) {
            ContextControlWrapper controlWrapper = ContextControlWrapper.getInstance();
            propertiesHolder = controlWrapper.getContextualReference(TestPropertiesHolder.class);
        }
    }

    @Override
    public Map<String, String> getProperties() {
        init();
        return propertiesHolder.getProperties();
    }

    @Override
    public Set<String> getPropertyNames() {
        init();
        return propertiesHolder.getProperties().keySet();
    }

    @Override
    public String getValue(String propertyName) {
        init();
        return propertiesHolder.getProperties().get(propertyName);
    }

    @Override
    public String getName() {
        return TEST_CONFIGURATION_NAME;
    }

}
