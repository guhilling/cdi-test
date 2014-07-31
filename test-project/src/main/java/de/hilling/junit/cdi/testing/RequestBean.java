package de.hilling.junit.cdi.testing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
