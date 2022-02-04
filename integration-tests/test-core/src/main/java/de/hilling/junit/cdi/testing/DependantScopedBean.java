package de.hilling.junit.cdi.testing;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class DependantScopedBean {

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private ProducedBean producedBean;

    @PostConstruct
    public void setup() {
        applicationBean.setAttribute(producedBean.getName());
    }

    public String getAttribute() {
        return applicationBean.getAttribute();
    }
}
