package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.OverriddenService;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;

@ExtendWith(CdiTestJunitExtension.class)
public class ActivateAlternativeForAlternativeTest {
    @Inject
    private TestActivatedOverridenService testOverride;
    @Inject
    private OverriddenService overriddenService;
    @Inject
    private BackendService backendService;

    @Test
    public void callTestActivatedService() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        assertEquals(2, testOverride.getInvocationCounter());
    }

    @Test
    public void callTestActivatedServiceIndependently() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        assertEquals(2, testOverride.getInvocationCounter());
    }

    @Test
    public void callOverridenServiceDirectly() {
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        assertEquals(3, testOverride.getInvocationCounter());
    }

    @Test
    public void callOverridenServiceMixed() {
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        backendService.storePerson(new Person());
        assertEquals(4, testOverride.getInvocationCounter());
    }
}
