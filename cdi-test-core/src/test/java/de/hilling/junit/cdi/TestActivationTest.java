package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(CdiUnitRunner.class)
public class TestActivationTest {
    @Inject
    private TestActivatedOverridenService overridenService;
    @Inject
    private BackendService backendService;

    @Test
    public void callTestActivatedService() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        Assert.assertEquals(2, overridenService.getInvocationCounter());
    }

    @Test
    public void callTestActivatedServiceIndependently() {
        backendService.storePerson(new Person());
        backendService.storePerson(new Person());
        Assert.assertEquals(2, overridenService.getInvocationCounter());
    }
}
