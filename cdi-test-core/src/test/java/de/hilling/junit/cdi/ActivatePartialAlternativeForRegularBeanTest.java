package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.junit.CdiTestJunitExtension;
import de.hilling.junit.cdi.service.BackendServiceTestPartialImplementation;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
class ActivatePartialAlternativeForRegularBeanTest {

    @Inject
    private SampleService sampleService;
    @Inject
    private BackendServiceTestPartialImplementation testBackendService;

    @Test
    void callTestActivatedService() {
        Person person = new Person();
        Assertions.assertThrows(IllegalStateException.class, () -> sampleService.storePerson(person));
    }

}
