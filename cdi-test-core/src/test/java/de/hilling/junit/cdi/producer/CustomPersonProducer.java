package de.hilling.junit.cdi.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import de.hilling.junit.cdi.beans.Person;

public class CustomPersonProducer {
    @Produces
    @CustomPerson
    public Person injectIntProperty(InjectionPoint injectionPoint) {
        Person person = new Person();
        String beanName = injectionPoint.getMember().getName();
        person.setName(beanName);
        return person;
    }

}
