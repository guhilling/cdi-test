package de.hilling.junit.cdi.testing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ApplicationBean extends BaseBean {

    @Inject
    private OtherApplicationBean otherApplicationBean;
}
