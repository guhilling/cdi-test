package de.hilling.junit.cdi.jpa;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class InvocationCounter {

    private AtomicInteger counter = new AtomicInteger();

    public void inc() {
        counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }

}
