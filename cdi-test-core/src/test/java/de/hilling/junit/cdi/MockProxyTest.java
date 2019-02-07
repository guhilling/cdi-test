package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
public class MockProxyTest {

    @Inject
    private SampleService sampleService;

    @Test
    public void createPersonWithMockBackend(@Mock BackendService backendService) {
        Person person = new Person();
        sampleService.storePerson(person);
        verify(backendService).storePerson(person);
    }

    @Test
    public void createPersonWithRealBackend() {
        Person person = new Person();
        sampleService.storePerson(person);
    }

}
