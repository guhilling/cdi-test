package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
public class ControllerTest {

    @Inject
    private Controller controller;

    @Test
    public void assertControllerInjected() {
        Assertions.assertNotNull(controller);
    }
}
