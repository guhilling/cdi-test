package de.hilling.junit.cdi.producer;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import de.hilling.junit.cdi.beans.Person;

public class CustomPersonProducer {
    @Produces
    @CustomPerson
    public Person injectIntProperty(InjectionPoint injectionPoint) {
        Person person = new Person();
        String beanName = injectionPoint.getBean().getName();
        person.setName(beanName);
        return person;
    }

}
