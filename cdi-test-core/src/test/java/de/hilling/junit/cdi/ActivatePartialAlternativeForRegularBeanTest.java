package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendServiceTestPartialImplementation;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
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
