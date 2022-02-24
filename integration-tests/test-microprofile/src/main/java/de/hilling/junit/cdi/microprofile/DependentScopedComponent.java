package de.hilling.junit.cdi.microprofile;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public class DependentScopedComponent {

    @Inject
    @ConfigProperty(name = "some.string.property")
    private String stringProperty;

    public String getStringProperty() {
        return stringProperty;
    }

}
