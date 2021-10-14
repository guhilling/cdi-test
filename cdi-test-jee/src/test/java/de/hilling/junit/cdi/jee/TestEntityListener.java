package de.hilling.junit.cdi.jee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class TestEntityListener {
    private static final Logger LOG = LoggerFactory.getLogger(TestEntityListener.class);

    @Inject
    private UpdateCounter updateCounter;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(Object o) {
        updateCounter.inc();
    }
}
