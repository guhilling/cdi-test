package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.beans.ConstructorInjected;
import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.beans.ResourceConstructorInjected;
import de.hilling.junit.cdi.beans.ResourceInjected;
import de.hilling.junit.cdi.producer.CustomPerson;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.OverriddenService;
import de.hilling.junit.cdi.service.TestQualifier;

/**
 * Test to check that all kinds of injections, including qualifiers and producers, do actually work.
 */
abstract class BaseInjectionTest {
    @Inject
    private Person                      person;
    @Inject
    private TestEnvironment             testInformation;
    @Inject
    private ConstructorInjected         constructorInjected;
    @Inject
    private ResourceInjected            resourceInjected;
    @Inject
    private ResourceConstructorInjected resourceConstructorInjected;
    @Inject
    private BackendService              backendService;
    @Inject
    private OverriddenService           sampleService;
    @TestQualifier
    @Inject
    private OverriddenService           qualifiedSampleService;
    @CustomPerson
    @Inject
    private Person                      customPerson;

    @Test
    void checkTestInformation() throws Exception {
        assertNotNull(testInformation);
        assertEquals(this.getClass(), testInformation.getTestClass());
        assertEquals("checkTestInformation()", testInformation.getTestName());
        assertEquals(this, testInformation.getTestInstance());
        assertEquals(BaseInjectionTest.class.getDeclaredMethod("checkTestInformation"), testInformation.getTestMethod());
    }

    @Test
    void testInjection() {
        assertNotNull(person);
        assertNotNull(customPerson);
        assertNotNull(constructorInjected);
    }

    @Test
    void testResourceInjection() {
        assertNotNull(resourceInjected);
        assertNotNull(resourceInjected.getRequest());
    }

    @Test
    void testResourceConstructorInjection() {
        assertNotNull(resourceConstructorInjected);
        assertNotNull(resourceConstructorInjected.getRequest());
    }

    @Test
    void testProxiedCostructorInjection() {
        assertNotNull(constructorInjected.getPerson());
        assertNotNull(constructorInjected.getRequest());
    }

    @Test
    void testPersons() {
        checkPersonWorks(person);
        checkPersonWorks(constructorInjected.getPerson());
    }

    @Test
    void qualifierInjectionWorkingOnTestedClass() {
        assertEquals("OverridingServiceImpl", backendService.storePerson(person));
        assertEquals("QualifiedOverriddenServiceImpl", backendService.storePersonQualified(person));
    }

    @Test
    void qualifierInjectionWorkingOnTestCase() {
        assertEquals("OverridingServiceImpl", sampleService.serviceMethod());
        assertEquals("QualifiedOverriddenServiceImpl", qualifiedSampleService.serviceMethod());
    }

    private void checkPersonWorks(Person person) {
        person.setName("test");
        assertEquals("test", person.getName());
    }
}
