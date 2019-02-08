package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import java.util.UUID;

@ExtendWith(CdiTestJunitExtension.class)
class ScopeTest {

    @Inject
    private Request request;

    private UUID lastIdentifier;

    @Test
    void assertNotNull() {
        Assertions.assertNotNull(request);
    }

    @Test
    void testOne() {
        assertNotEqual();
    }

    @Test
    void testTwo() {
        assertNotEqual();
    }

    private void assertNotEqual() {
        if (lastIdentifier == null) {
            lastIdentifier = request.getIdentifier();
        } else {
            Assertions.assertNotEquals(lastIdentifier, request.getIdentifier());
        }
    }

}
