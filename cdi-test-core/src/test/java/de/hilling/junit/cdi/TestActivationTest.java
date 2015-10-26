package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.OverriddenService;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(CdiUnitRunner.class)
public class TestActivationTest {
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
        Assert.assertEquals(2, testOverride.getInvocationCounter());
    }

    @Test
    public void callTestActivatedServiceIndependently() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        Assert.assertEquals(2, testOverride.getInvocationCounter());
    }

    @Test
    public void callOverridenServiceDirectly() {
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        Assert.assertEquals(3, testOverride.getInvocationCounter());
    }

    @Test
    public void callOverridenServiceMixed() {
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        overriddenService.serviceMethod();
        backendService.storePerson(new Person());
        Assert.assertEquals(4, testOverride.getInvocationCounter());
    }
}
