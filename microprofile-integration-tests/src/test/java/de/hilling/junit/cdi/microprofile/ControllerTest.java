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
    public void controllerInjected() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void useValuesFromPropertyFile() {
        Assertions.assertEquals("value class a", controller.getStringProperty());
        Assertions.assertEquals(50, controller.getIntegerProperty());
        Assertions.assertTrue(controller.getBoolProperty());
        Assertions.assertEquals(40000L, controller.getLongProperty());
    }

    @Test
    @ConfigPropertyValue(name = "some.string.property", value = "value method b")
    @ConfigPropertyValue(name = "some.integer.property", value = "4")
    @ConfigPropertyValue(name = "some.long.property", value = "46000")
    public void overrideValuesInMethod() {
        Assertions.assertEquals("value method b", controller.getStringProperty());
        Assertions.assertEquals(4, controller.getIntegerProperty());
        Assertions.assertTrue(controller.getBoolProperty());
        Assertions.assertEquals(46000L, controller.getLongProperty());
    }

    @Test
    @ConfigPropertyValue(name = "some.string.property", value = "value method c")
    @ConfigPropertyValue(name = "some.integer.property", value = "5")
    @ConfigPropertyValue(name = "some.long.property", value = "47000")
    @ConfigPropertyValue(name = "some.boolean.property", value = "fase")
    public void overrideValuesDifferentlyInOtherMethod() {
        Assertions.assertEquals("value method c", controller.getStringProperty());
        Assertions.assertEquals(5, controller.getIntegerProperty());
        Assertions.assertFalse(controller.getBoolProperty());
        Assertions.assertEquals(47000L, controller.getLongProperty());
    }
}
