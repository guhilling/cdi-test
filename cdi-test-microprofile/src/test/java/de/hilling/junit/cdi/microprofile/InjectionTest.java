package de.hilling.junit.cdi.microprofile;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@ConfigPropertyValue(name = "some.string.property", value = "value class a")
class InjectionTest {


    @Inject
    private InjectionSample injectionSample;

    @Test
    void testDefaultValue() {
        Assertions.assertEquals("value class a", injectionSample.getSomeProperty());
    }
}
