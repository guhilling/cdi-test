package de.hilling.junit.cdi.microprofile;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public class InjectionSample {

    @ConfigProperty(name = "some.string.property")
    @Inject
    private String someProperty;

    public String getSomeProperty() {
        return someProperty;
    }
}
