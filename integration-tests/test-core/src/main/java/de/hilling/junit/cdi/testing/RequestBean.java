package de.hilling.junit.cdi.testing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class RequestBean extends BaseBean {

    @Inject
    private ApplicationBean applicationBean;

    @Override
    public void setAttribute(String attribute) {
        applicationBean.setAttribute(attribute);
        super.setAttribute(attribute);
    }
}
