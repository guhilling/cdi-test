package de.hilling.junit.cdi.jpa;

import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class CounterEntityListener {

    @Inject
    private InvocationCounter invocationCounter;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(UserEntity user) {
        invocationCounter.inc();
    }
}
