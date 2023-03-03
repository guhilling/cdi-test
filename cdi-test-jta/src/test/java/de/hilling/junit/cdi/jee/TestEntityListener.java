package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class TestEntityListener {
    @Inject
    private UpdateCounter updateCounter;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(Object o) {
        updateCounter.inc();
    }
}
