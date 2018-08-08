package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.beans.ConstructorInjected;
import de.hilling.junit.cdi.beans.Person;

public class InjectionTest extends CdiTestAbstract {

    @Inject
    private Person person;

    @Inject
    private TestContext testInformation;

    @Inject
    private ConstructorInjected constructorInjected;


    @Test
    public void checkTestInformation()throws Exception {
        assertNotNull(testInformation);
        assertEquals(InjectionTest.class, testInformation.getTestClass());
        assertEquals(InjectionTest.class.getMethod("checkTestInformation"), testInformation.getTestMethod());
    }

    @Test
    public void testInjection() {
        assertNotNull(person);
        assertNotNull(constructorInjected);
    }

    @Test
    public void testProxiedCostructorInjection() {
        assertNotNull(constructorInjected.getPerson());
        assertNotNull(constructorInjected.getRequest());
    }

    @Test
    public void testPersons() {
        checkPersonWorks(person);
        checkPersonWorks(constructorInjected.getPerson());
    }

    private void checkPersonWorks(Person person) {
        person.setName("test");
        assertEquals("test", person.getName());
    }

}
