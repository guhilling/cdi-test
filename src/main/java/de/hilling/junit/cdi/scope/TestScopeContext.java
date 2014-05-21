package de.hilling.junit.cdi.scope;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import de.hilling.junit.cdi.scope.TestScopeContextHolder.TestScopeInstance;

public class TestScopeContext implements Context, Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(TestScopeContext.class
			.getCanonicalName());

	private TestScopeContextHolder customScopeContextHolder;

	public TestScopeContext() {
		LOG.info("Init");
		this.customScopeContextHolder = TestScopeContextHolder.getInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final Contextual<T> contextual) {
		Bean<T> bean = (Bean<T>) contextual;
		if (customScopeContextHolder.getBeans()
				.containsKey(bean.getBeanClass())) {
			return (T) customScopeContextHolder.getBean(bean.getBeanClass()).instance;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final Contextual<T> contextual,
			final CreationalContext<T> creationalContext) {
		Bean<T> bean = (Bean<T>) contextual;
		if (customScopeContextHolder.getBeans()
				.containsKey(bean.getBeanClass())) {
			return (T) customScopeContextHolder.getBean(bean.getBeanClass()).instance;
		} else {
			T t = (T) bean.create(creationalContext);
			TestScopeInstance<T> customInstance = new TestScopeInstance<>();
			customInstance.bean = bean;
			customInstance.ctx = creationalContext;
			customInstance.instance = t;
			customScopeContextHolder.putBean(customInstance);
			return t;
		}
	}

	@Override
	public Class<? extends Annotation> getScope() {
		return TestScope.class;
	}

	public boolean isActive() {
		return true;
	}

}
