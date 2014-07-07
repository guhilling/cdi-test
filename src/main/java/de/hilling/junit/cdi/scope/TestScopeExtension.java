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
	
	/**
	 * add contexts after bean discovery.
	 * @param afterBeanDiscovery
	 * @param beanManager
	 */
	public void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscovery,
			BeanManager beanManager) {
		addContexts(afterBeanDiscovery);
	}

	private void addContexts(AfterBeanDiscovery abd) {
		abd.addContext(new TestSuiteContext());
		abd.addContext(new TestContext());
	}

	/**
	 * replace injections with proxies.
	 * @param injectionTarget
	 */
	public <X> void processInjectionTarget(
			@Observes ProcessInjectionTarget<X> injectionTarget) {
		InjectionTarget<X> wrapper = new ProxyInjectionTarget<X>(injectionTarget.getInjectionTarget());

		injectionTarget.setInjectionTarget(wrapper);
	}

}