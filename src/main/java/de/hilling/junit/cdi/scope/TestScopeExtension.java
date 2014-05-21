package de.hilling.junit.cdi.scope;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class TestScopeExtension implements Extension, Serializable {
	private static final long serialVersionUID = 1L;

	public void registerContext(@Observes final AfterBeanDiscovery event) {
		event.addContext(new TestSuiteContext());
		event.addContext(new TestCaseContext());
	}
}