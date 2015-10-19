package de.hilling.cdi.sampleapp.controller;

import de.hilling.cdi.sampleapp.ejb.RegistrationService;
import de.hilling.junit.cdi.CdiUnitRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;

@RunWith(CdiUnitRunner.class)
public class RegistrationBeanTest {

    @Inject
    private RegistrationBean registrationBean;

    @Mock
    private RegistrationService service;

    @Before
    public void setUp() {
        registrationBean.setAge(10);
        registrationBean.setName("Gunnar");
    }

    @Test
    public void checkValues() {
        Assert.assertEquals("Gunnar", registrationBean.getName());
        Assert.assertEquals(10, registrationBean.getAge());
    }

    @Test
    public void register() {
        registrationBean.register();
        Mockito.verify(service).register("Gunnar");
    }

}