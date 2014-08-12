package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.beans.Person;

import javax.inject.Inject;

public class SampleService {

    @Inject
    private BackendService backendService;

    public void storePerson(Person person) {
        backendService.storePerson(person);
    }
}
