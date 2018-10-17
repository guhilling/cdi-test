package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(CdiTestJunitExtension.class)
public class SimpleTest {

    @Inject
    private Person person;

    @Test
    public void testInjection() {
        assertNotNull(person);
    }

}
