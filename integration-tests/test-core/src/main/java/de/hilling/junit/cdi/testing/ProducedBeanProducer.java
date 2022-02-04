package de.hilling.junit.cdi.testing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

@RequestScoped
public class ProducedBeanProducer {

    @Produces
    public ProducedBean createProducedBean() {
        return new ProducedBean("hello");
    }
}
