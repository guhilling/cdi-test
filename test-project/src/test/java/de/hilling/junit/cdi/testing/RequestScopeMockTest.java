package de.hilling.junit.cdi.testing;

import javax.inject.Inject;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class RequestScopeMockTest extends BaseTest {

	private static final String SAMPLE = "sample";

	@Mock
	private ApplicationBean applicationBean;

	@Inject
	private RequestBean requestBean;

	@Test
	public void setAttributeTransitive() {
		requestBean.setAttribute(SAMPLE);
		Mockito.verify(applicationBean).setAttribute(SAMPLE);
	}

}
