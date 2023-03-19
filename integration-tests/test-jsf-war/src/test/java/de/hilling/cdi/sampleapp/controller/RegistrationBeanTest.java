package de.hilling.cdi.sampleapp.controller;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hilling.cdi.sampleapp.ejb.RegistrationService;
import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
class RegistrationBeanTest {

    @Inject
    private RegistrationBean registrationBean;

    @Mock
    private RegistrationService service;

    @BeforeEach
    public void setUp() {
        registrationBean.setAge(10);
        registrationBean.setName("Gunnar");
    }

    @Test
    void checkValues() {
        Assertions.assertEquals("Gunnar", registrationBean.getName());
        Assertions.assertEquals(10, registrationBean.getAge());
    }

    @Test
    void register() {
        registrationBean.register();
        Mockito.verify(service).register("Gunnar");
    }

}