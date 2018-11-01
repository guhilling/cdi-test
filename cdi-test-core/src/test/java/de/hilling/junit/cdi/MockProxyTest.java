package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@ExtendWith(CdiTestJunitExtension.class)
public class MockProxyTest {

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
