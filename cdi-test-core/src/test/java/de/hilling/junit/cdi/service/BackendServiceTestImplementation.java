package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.beans.Person;

@ActivatableTestImplementation(BackendService.class)
public class BackendServiceTestImplementation extends BackendService {

    private int invocations;

    @Override
    public void storePerson(Person person) {
        invocations++;
    }

    public int getInvocations() {
        return invocations;
    }
}
