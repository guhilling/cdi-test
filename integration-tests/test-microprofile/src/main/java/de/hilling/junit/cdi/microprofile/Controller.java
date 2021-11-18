package de.hilling.junit.cdi.microprofile;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Controller implements ControllerService {

    @Inject
    @ConfigProperty(name = "some.string.property")
    private String stringProperty;

    @Inject
    @ConfigProperty(name = "some.integer.property")
    private Integer intProperty;

    @Inject
    @ConfigProperty(name = "some.boolean.property")
    private Boolean boolProperty;

    @Inject
    @ConfigProperty(name = "some.long.property")
    private Long longProperty;

    @Inject
    @ConfigProperty(name = "some.horse.property")
    private Horse horseProperty;

    @Override
    public String getStringProperty() {
        return stringProperty;
    }

    @Override
    public int getIntegerProperty() {
        return intProperty;
    }

    @Override
    public boolean getBoolProperty() {
        return boolProperty;
    }

    @Override
    public long getLongProperty() {
        return longProperty;
    }

    @Override
    public String getHorseProperty() {
        return horseProperty.getName();
    }

}
