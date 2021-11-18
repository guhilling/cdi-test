package de.hilling.junit.cdi.scopedbeans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class RequestScopedBean extends ScopedBean {

    @Inject
    private DependentScopedBean dependentScopedBean;

    public DependentScopedBean getDependentScopedBean() {
        return dependentScopedBean;
    }

}