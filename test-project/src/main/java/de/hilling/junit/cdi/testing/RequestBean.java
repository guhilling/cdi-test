package de.hilling.junit.cdi.testing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import de.hilling.junit.cdi.scope.Mockable;

@RequestScoped
@Mockable
public class RequestBean extends BaseBean {

	@Inject
	private ApplicationBean applicationBean;

	@Override
	public void setAttribute(String attribute) {
		applicationBean.setAttribute(attribute);
		super.setAttribute(attribute);
	}
}
