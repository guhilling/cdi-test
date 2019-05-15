package de.hilling.junit.cdi.microprofile;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

class ControllerTestIT {


    private ControllerService controllerService;

    @BeforeEach
    void setUp() throws Exception {
        URI apiUri = new URI("http://127.0.0.1:8080/");
        controllerService = RestClientBuilder.newBuilder()
                                             .baseUri(apiUri)
                                             .build(ControllerService.class);
    }

    @Test
    void assertStringProperty() {
        Assertions.assertEquals("Message from test", controllerService.getStringProperty());
    }

    @Test
    void assertIntegerProperty() {
        Assertions.assertEquals(915, controllerService.getIntegerProperty());
    }

    @Test
    void assertLongProperty() {
        Assertions.assertEquals(81508150815081L, controllerService.getLongProperty());
    }

    @Test
    void assertBooleanProperty() {
        Assertions.assertTrue(controllerService.getBoolProperty());
    }

    @Test
    void assertHorseProperty() {
        Assertions.assertEquals("Rih", controllerService.getHorseProperty());
    }
}
