package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.beans.Person;

import javax.enterprise.inject.Typed;

@ActivatableTestImplementation(BackendService.class)
@Typed(BackendServiceTestImplementation.class)
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
