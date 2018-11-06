package de.hilling.junit.cdi.microprofile;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

public class ControllerTestIT {


    private ControllerService controllerService;

    @BeforeEach
    public void setUp() throws Exception {
        URI apiUri = new URI("http://127.0.0.1:8080/");
        controllerService = RestClientBuilder.newBuilder()
                                             .baseUri(apiUri)
                                             .build(ControllerService.class);
    }

    @Test
    public void assertStringProperty() {
        Assertions.assertEquals("Message from test", controllerService.getStringProperty());
    }

    @Test
    public void assertIntegerProperty() {
        Assertions.assertEquals(915, controllerService.getIntegerProperty());
    }

    @Test
    public void assertLongProperty() {
        Assertions.assertEquals(81508150815081L, controllerService.getLongProperty());
    }

    @Test
    public void assertBooleanProperty() {
        Assertions.assertTrue(controllerService.getBoolProperty());
    }

    @Test
    public void assertHorseProperty() {
        Assertions.assertEquals("Rih", controllerService.getHorseProperty());
    }
}
