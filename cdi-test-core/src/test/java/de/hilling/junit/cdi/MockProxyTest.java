package de.hilling.junit.cdi;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;

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
        verifyZeroInteractions(backendService);
    }
}
