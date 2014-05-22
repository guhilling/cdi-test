package de.hilling.junit.cdi.scope.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public class TestScopeContextHolder {
	private Map<Class<?>, TestScopeInstance<?>> beans;

	public TestScopeContextHolder() {
		beans = Collections.synchronizedMap(new HashMap<Class<?>, TestScopeInstance<?>>());
	}

	public Map<Class<?>, TestScopeInstance<?>> getBeans() {
		return beans;
	}

	@SuppressWarnings("unchecked")
	public <T> TestScopeInstance<T> getBean(Class<T> type) {
		return (TestScopeInstance<T>) getBeans().get(type);
	}

	public <T> void putBean(TestScopeInstance<T> customInstance) {
		beans.put(customInstance.bean.getBeanClass(), customInstance);
	}

	public void clear() {
		for (TestScopeInstance<?> scopeInstance : beans.values()) {
			destroy(scopeInstance);
		}
		beans.clear();
	}

	private <T> void destroy(TestScopeInstance<T> scopeInstance) {
		Bean<T> bean = scopeInstance.bean;
		bean.destroy(scopeInstance.instance, scopeInstance.ctx);
	}

	public static class TestScopeInstance<T> {
		Bean<T> bean;
		CreationalContext<T> ctx;
		T instance;
	}

}
