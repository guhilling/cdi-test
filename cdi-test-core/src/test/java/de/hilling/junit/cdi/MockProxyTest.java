package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Show mock injection using test method arguments as supported by the Mockito extension.
 */
@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
class MockProxyTest {

    @Inject
    private SampleService sampleService;

    @Test
    void createPersonWithMockBackend(@Mock BackendService backendService) {
        Person person = new Person();
        sampleService.storePerson(person);
        verify(backendService).storePerson(person);
    }

}
