package de.hilling.junit.cdi;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.beans.Request;

public class ScopeTest extends CdiTestAbstract {

    @Inject
    private Request request;

    private UUID lastIdentifier;

    @Test
    public void assertNotNull() {
        Assertions.assertNotNull(request);
    }

    @Test
    public void testOne() {
        assertNotEqual();
    }

    @Test
    public void testTwo() {
        assertNotEqual();
    }

    private void assertNotEqual() {
        if (lastIdentifier == null) {
            request.getIdentifier();
        } else {
            Assertions.assertNotEquals(lastIdentifier, request.getIdentifier());
        }
    }

}
