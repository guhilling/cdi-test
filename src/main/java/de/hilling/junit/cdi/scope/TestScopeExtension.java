package de.hilling.junit.cdi.scope;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import de.hilling.junit.cdi.proxy.ProxyInjectionTarget;

public class TestScopeExtension implements Extension, Serializable {
	private static final long serialVersionUID = 1L;

	public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd,
			BeanManager bm) {
		addContexts(abd);
	}

	private void addContexts(AfterBeanDiscovery abd) {
		abd.addContext(new TestSuiteContext());
		abd.addContext(new TestContext());
	}

	public <X> void processInjectionTarget(
			@Observes ProcessInjectionTarget<X> pit) {
		InjectionTarget<X> wrapper = new ProxyInjectionTarget<X>(pit.getInjectionTarget());

		pit.setInjectionTarget(wrapper);
	}

}