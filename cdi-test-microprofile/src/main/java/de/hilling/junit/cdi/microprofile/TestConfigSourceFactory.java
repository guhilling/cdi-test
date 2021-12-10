package de.hilling.junit.cdi.microprofile;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;

import static de.hilling.junit.cdi.microprofile.TestScopedConfiguration.PRIORITY;

import java.util.ArrayList;
import java.util.OptionalInt;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class TestConfigSourceFactory implements ConfigSourceFactory {

    private static final TestScopedConfiguration testScopedConfiguration = new TestScopedConfiguration();

    @Override
    public OptionalInt getPriority() {
        return OptionalInt.of(PRIORITY);
    }

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
        ArrayList<ConfigSource> sources = new ArrayList<>();
        sources.add(testScopedConfiguration);
        return sources;
    }
}
