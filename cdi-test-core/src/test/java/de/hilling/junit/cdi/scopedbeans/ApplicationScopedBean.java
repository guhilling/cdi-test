package de.hilling.junit.cdi.scopedbeans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ApplicationScopedBean extends ScopedBean {

    @Inject
    private DependentScopedBean dependentScopedBean;

    public DependentScopedBean getDependentScopedBean() {
        return dependentScopedBean;
    }
}