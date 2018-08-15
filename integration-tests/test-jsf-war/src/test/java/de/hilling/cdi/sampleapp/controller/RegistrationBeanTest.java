package de.hilling.cdi.sampleapp.controller;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import de.hilling.cdi.sampleapp.ejb.RegistrationService;
import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
public class RegistrationBeanTest {

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
    public void checkValues() {
        Assertions.assertEquals("Gunnar", registrationBean.getName());
        Assertions.assertEquals(10, registrationBean.getAge());
    }

    @Test
    public void register() {
        registrationBean.register();
        Mockito.verify(service).register("Gunnar");
    }

}