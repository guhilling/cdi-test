package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;
import java.util.UUID;

/**
 * Test and demo different scopes in cdi-test.
 */
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
