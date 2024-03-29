package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendServiceTestImplementation;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Demo and test auto-wiring of {@link Inject}ed test implementations.
 */
@ExtendWith(CdiTestJunitExtension.class)
class ActivateAlternativeForRegularBeanTest {
    @Inject
    private SampleService sampleService;
    @Inject
    private BackendServiceTestImplementation testBackendService;

    @Test
    void callTestActivatedService() {
        sampleService.storePerson(new Person());
        assertEquals(1, testBackendService.getInvocations());
    }

    @Test
    @BackendServiceException(RuntimeException.class)
    void callTestActivatedServiceWithBackendException() {
        Person person = new Person();
        Assertions.assertThrows(RuntimeException.class, () -> sampleService.storePerson(person));
    }

}
