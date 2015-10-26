package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.AlternativeFor;

import javax.annotation.PostConstruct;

@AlternativeFor(OverridingServiceImpl.class)
public class TestActivatedOverridenService extends OverridingServiceImpl {

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
