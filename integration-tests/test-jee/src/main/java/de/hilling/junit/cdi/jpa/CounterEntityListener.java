package de.hilling.junit.cdi.jpa;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

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
