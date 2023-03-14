package de.hilling.junit.cdi.jee;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class UpdateCounter {

    private final AtomicInteger counter = new AtomicInteger();

    public void inc() {
        counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }
}
