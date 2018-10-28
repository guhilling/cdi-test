package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
@ConfigPropertyValue(name = "some.string.property", value = "value class a")
public class ControllerTest {

    @Inject
    private Controller controller;

    @Test
    @ConfigPropertyValue(name = "some.string.property", value = "value method b")
    @ConfigPropertyValue(name = "some.integer.property", value = "1")
    public void assertControllerInjected() {
        Assertions.assertNotNull(controller);
        System.out.println("string value: " + controller.getStringProperty());
        System.out.println("integer value: " + controller.getIntegerProperty());
        System.out.println("boolean value: " + controller.getBoolProperty());
        System.out.println("integer value: " + controller.getLongProperty());
    }
}
