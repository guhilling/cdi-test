package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;

import javax.annotation.PostConstruct;

@ActivatableTestImplementation(OverridingServiceImpl.class)
public class TestActivatedOverridenService implements OverriddenService {

    private int invocationCounter = 0;

    @PostConstruct
    protected void create() {
    }

    public int getInvocationCounter() {
        return invocationCounter;
    }

    @Override
    public void serviceMethod() {
        invocationCounter++;
    }
}
