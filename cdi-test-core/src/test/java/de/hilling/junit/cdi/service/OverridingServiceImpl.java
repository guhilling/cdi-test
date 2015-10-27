package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;

@GlobalTestImplementation
public class OverridingServiceImpl implements OverriddenService {
    @Override
    public void serviceMethod() {
    }
}
