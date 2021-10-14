package de.hilling.junit.cdi.service;

import jakarta.annotation.PostConstruct;

@TestQualifier
public class QualifiedOverriddenServiceImpl implements OverriddenService {

    @PostConstruct
    protected void create() {
    }

    @Override
    public String serviceMethod() {
        return "QualifiedOverriddenServiceImpl";
    }
}
