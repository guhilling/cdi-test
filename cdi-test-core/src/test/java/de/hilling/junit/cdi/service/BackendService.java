package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.beans.Person;

import javax.inject.Inject;

public class BackendService {

    @Inject
    private OverriddenService sampleService;

    public void storePerson(Person person) {
        sampleService.serviceMethod();
    }

}
