package de.hilling.junit.cdi.testing;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;

@Stateless
public class ProducedBeanProducer {

    @Produces
    public ProducedBean createProducedBean() {
        return new ProducedBean("hello");
    }
}
