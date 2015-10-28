package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendServiceTestImplementation;
import de.hilling.junit.cdi.service.BackendServiceTestPartialImplementation;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(CdiUnitRunner.class)
public class ActivatePartialAlternativeForRegularBeanTest {
    @Inject
    private SampleService sampleService;
    @Inject
    private BackendServiceTestPartialImplementation testBackendService;

    @Test
    public void callTestActivatedService() {
        sampleService.storePerson(new Person());
    }

}
