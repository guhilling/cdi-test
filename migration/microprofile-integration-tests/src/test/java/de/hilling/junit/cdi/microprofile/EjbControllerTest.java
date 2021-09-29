package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
@ConfigPropertyValue(name = "some.string.property", value = "value class a")
class EjbControllerTest {

    @Inject
    private EjbController controllerService;

    @Test
    void controllerInjected() {
        Assertions.assertNotNull(controllerService);
    }

    @Test
    void useValuesFromPropertyFile() {
        Assertions.assertEquals("value class a", controllerService.getStringProperty());
        Assertions.assertEquals(50, controllerService.getIntegerProperty());
        Assertions.assertTrue(controllerService.getBoolProperty());
        Assertions.assertEquals(40000L, controllerService.getLongProperty());
        Assertions.assertEquals("Black Beauty", controllerService.getHorseProperty());
    }

    @Test
    @ConfigPropertyValue(name = "some.string.property", value = "value method b")
    @ConfigPropertyValue(name = "some.integer.property", value = "4")
    @ConfigPropertyValue(name = "some.long.property", value = "46000")
    void overrideValuesInMethod() {
        Assertions.assertEquals("value method b", controllerService.getStringProperty());
        Assertions.assertEquals(4, controllerService.getIntegerProperty());
        Assertions.assertTrue(controllerService.getBoolProperty());
        Assertions.assertEquals(46000L, controllerService.getLongProperty());
    }

    @Test
    @ConfigPropertyValue(name = "some.string.property", value = "value method c")
    @ConfigPropertyValue(name = "some.integer.property", value = "5")
    @ConfigPropertyValue(name = "some.long.property", value = "47000")
    @ConfigPropertyValue(name = "some.boolean.property", value = "fase")
    void overrideValuesDifferentlyInOtherMethod() {
        Assertions.assertEquals("value method c", controllerService.getStringProperty());
        Assertions.assertEquals(5, controllerService.getIntegerProperty());
        Assertions.assertFalse(controllerService.getBoolProperty());
        Assertions.assertEquals(47000L, controllerService.getLongProperty());
    }
}
