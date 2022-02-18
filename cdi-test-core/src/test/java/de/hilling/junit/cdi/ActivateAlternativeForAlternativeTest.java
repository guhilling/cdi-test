package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.OverriddenService;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Demo and test test activation for alternatives.
 */
@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
class ActivateAlternativeForAlternativeTest {
    @Inject
    private TestActivatedOverridenService testOverride;
    @Inject
    private OverriddenService overriddenService;
    @Inject
    private BackendService backendService;

    @Test
    void callTestActivatedService() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        assertEquals(2, testOverride.getInvocationCounter());
    }

    @Test
    void callTestActivatedServiceIndependently() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        assertEquals(2, testOverride.getInvocationCounter());
    }

    @Test
    void callOverridenServiceDirectly() {
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        assertEquals(3, testOverride.getInvocationCounter());
    }

    @Test
    void callOverridenServiceMixed() {
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        backendService.storePerson(new Person());
        assertEquals(4, testOverride.getInvocationCounter());
    }
}
