package de.hilling.junit.cdi.service;

import javax.annotation.PostConstruct;

public class OverriddenServiceImpl implements OverriddenService {

    @PostConstruct
    protected void create() {
        throw new RuntimeException("not working");
    }

    @Override
    public void serviceMethod() {
    }
}
