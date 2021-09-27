package de.hilling.junit.cdi.jee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class TestEntityListener {
    private static final Logger LOG = LoggerFactory.getLogger(TestEntityListener.class);

    @Inject
    private UpdateCounter updateCounter;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(UserEntity user) {
        LOG.info("got entity {}", user);
        updateCounter.inc();
        if (user.getId() == 0) {
            LOG.info("[USER AUDIT] About to add a user");
        } else {
            LOG.info("[USER AUDIT] About to update/delete user: " + user.getId());
        }
    }
}
