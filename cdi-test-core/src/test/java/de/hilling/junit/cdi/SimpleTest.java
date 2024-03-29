package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Showcase for trivial test.
 */
@ExtendWith(CdiTestJunitExtension.class)
class SimpleTest {

    @Inject
    private Person person;

    @Test
    void testInjection() {
        assertNotNull(person);
    }

}
