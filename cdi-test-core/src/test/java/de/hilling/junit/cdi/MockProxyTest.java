package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

public class MockProxyTest extends CdiTestAbstract {

    @Mock
    private BackendService backendService;

    @Inject
    private SampleService sampleService;

    @Test
    public void createPerson() {
        Person person = new Person();
        sampleService.storePerson(person);
        verify(backendService).storePerson(person);
    }

    @Test
    public void doNothing() {
        Mockito.verifyZeroInteractions(backendService);
    }
}
