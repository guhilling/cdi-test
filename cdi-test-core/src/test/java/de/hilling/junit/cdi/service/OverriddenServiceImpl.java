package de.hilling.junit.cdi.service;

import jakarta.annotation.PostConstruct;

public class OverriddenServiceImpl implements OverriddenService {

    @PostConstruct
    protected void create() {
        throw new RuntimeException("not working");
    }

    @Override
    public String serviceMethod() {
        return "OverriddenServiceImpl";
    }
}
