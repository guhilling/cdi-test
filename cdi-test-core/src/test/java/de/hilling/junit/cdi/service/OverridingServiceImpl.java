package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.annotations.TestImplementation;

@TestImplementation
public class OverridingServiceImpl implements OverriddenService {
    @Override
    public void serviceMethod() {
    }
}
