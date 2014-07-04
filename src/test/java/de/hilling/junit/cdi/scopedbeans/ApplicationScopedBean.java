package de.hilling.junit.cdi.scopedbeans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationScopedBean extends ScopedBean {

	@Inject
	private DependentScopedBean dependentScopedBean;

	public DependentScopedBean getDependentScopedBean() {
		return dependentScopedBean;
	}
}