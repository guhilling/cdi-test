package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.beans.Person;

import javax.inject.Inject;

public class BackendService {

    @Inject
    private OverriddenService sampleService;

    @TestQualifier
    @Inject
    private OverriddenService qualifiedSampleService;

    public String storePerson(Person person) {
        return sampleService.serviceMethod();
    }

    public String storePersonQualified(Person person) {
        return qualifiedSampleService.serviceMethod();
    }

}
