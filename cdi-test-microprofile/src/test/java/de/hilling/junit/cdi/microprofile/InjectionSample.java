package de.hilling.junit.cdi.microprofile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

@RequestScoped
public class InjectionSample {

    @ConfigProperty(name = "some.string.property", defaultValue = "default")
    @Inject
    private String someProperty;

    public String getSomeProperty() {
        return someProperty;
    }
}
