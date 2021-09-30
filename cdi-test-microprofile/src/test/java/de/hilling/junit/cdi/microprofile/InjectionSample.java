package de.hilling.junit.cdi.microprofile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Inject
    @ConfigProperty(name = "some.float.property", defaultValue = "1.0")
    private Float floatProperty;

    @Inject
    @ConfigProperty(name = "some.double.property", defaultValue = "1.0")
    private Double doubleProperty;

    @Inject
    @ConfigProperty(name = "some.optional.property")
    private Optional<String> optionalProperty;

    @Inject
    @ConfigProperty(name = "some.list.property", defaultValue = "a,a,b")
    private List<String> listProperty;

    @Inject
    @ConfigProperty(name = "some.set.property", defaultValue = "a,a,b")
    private Set<String> setProperty;

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

    public Float getFloatProperty() {
        return floatProperty;
    }

    public Double getDoubleProperty() {
        return doubleProperty;
    }

    public Optional<String> getOptionalProperty() {
        return optionalProperty;
    }

    public List<String> getListProperty() {
        return listProperty;
    }

    public Set<String> getSetProperty() {
        return setProperty;
    }
}
