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

    @Inject
    @ConfigProperty(name = "some.string.property", defaultValue = "default")
    private String stringProperty;

    @Inject
    @ConfigProperty(name = "some.integer.property", defaultValue = "1")
    private Integer intProperty;

    @Inject
    @ConfigProperty(name = "some.boolean.property", defaultValue = "true")
    private Boolean boolProperty;

    @Inject
    @ConfigProperty(name = "some.long.property", defaultValue = "1")
    private Long longProperty;


    public String getSomeProperty() {
        return someProperty;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public Integer getIntProperty() {
        return intProperty;
    }

    public Boolean getBoolProperty() {
        return boolProperty;
    }

    public Long getLongProperty() {
        return longProperty;
    }
}
