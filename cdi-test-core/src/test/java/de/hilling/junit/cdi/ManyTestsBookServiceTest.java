package de.hilling.junit.cdi;

import jakarta.inject.Inject;

import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendServiceTestImplementation;
import de.hilling.junit.cdi.service.SampleService;

/**
 * Demo for running lots of (parameterized) junit 5 tests _fast_.
 *
 * <p>
 *     <em>Do not try to use JUnit 5 Dynamic Tests as they use a different lifecycle model!</em>
 * </p>
 */
@ExtendWith(CdiTestJunitExtension.class)
class ParameterizedTestLifecycleTest {

    @Inject
    private SampleService service;

    @Inject
    private BackendServiceTestImplementation backendService;

    static Stream<String> createPersonNames() {
        return Stream.
        generate(UUID::randomUUID).
        map(UUID::toString).
        limit(5);
    }

    @ParameterizedTest
    @MethodSource("createPersonNames")
    void storePerson(String name) {
        Person testPerson = new Person(name);
        service.storePerson(testPerson);
        Assertions.assertEquals(1, backendService.getInvocations());
    }
}