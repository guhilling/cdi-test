package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import io.smallrye.config.inject.ConfigProducerUtil;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.*;

@TestSuiteScoped
public class TestConfigProducer implements Serializable {

    @Inject
    private Config config;

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    String produceStringConfigProperty(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Long getLongValue(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Integer getIntegerValue(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Float produceFloatConfigProperty(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Double produceDoubleConfigProperty(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Boolean produceBooleanConfigProperty(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    <T> Optional<T> produceOptionalConfigValue(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    <T> Set<T> producesSetConfigProperty(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    <T> List<T> producesListConfigProperty(InjectionPoint ip) {
        return ConfigProducerUtil.getValue(ip, config);
    }

}
