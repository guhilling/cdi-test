package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.inject.Inject;

import static org.mockito.Mockito.verify;

/**
 * Demo and test {@link de.hilling.junit.cdi.scope.TestScoped} and separation of test cases.
 */
@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
class MultipleInvocationsTest {

    @Inject
    private SampleService sampleService;

    @Mock
    private BackendService backendService;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
    }

    @Test
    void createPersonWithMockBackendA() {
        sampleService.storePerson(person);
        verify(backendService).storePerson(person);
    }

    @Test
    void createPersonWithMockBackendB() {
        sampleService.storePerson(person);
        verify(backendService).storePerson(person);
    }

}
