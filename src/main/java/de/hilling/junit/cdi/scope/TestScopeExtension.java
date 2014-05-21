package de.hilling.junit.cdi.scope;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class TestScopeExtension implements Extension, Serializable {
	private static final long serialVersionUID = 1L;

	public void addScope(@Observes final BeforeBeanDiscovery event) {
		event.addScope(TestScope.class, true, false);
	}

	public void registerContext(@Observes final AfterBeanDiscovery event) {
		event.addContext(new TestScopeContext());
	}
}