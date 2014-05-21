package de.hilling.junit.cdi.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public class TestScopeContextHolder {
	private static TestScopeContextHolder INSTANCE = new TestScopeContextHolder();
	private Map<Class<?>, TestScopeInstance<?>> beans;

	private TestScopeContextHolder() {
		beans = Collections.synchronizedMap(new HashMap<Class<?>, TestScopeInstance<?>>());
	}

	public synchronized static TestScopeContextHolder getInstance() {
		return INSTANCE;
	}

	public Map<Class<?>, TestScopeInstance<?>> getBeans() {
		return beans;
	}

	@SuppressWarnings("unchecked")
	public <T> TestScopeInstance<T> getBean(Class<T> type) {
		return (TestScopeInstance<T>) getBeans().get(type);
	}

	public <T> void putBean(TestScopeInstance<T> customInstance) {
		getBeans().put(customInstance.bean.getBeanClass(), customInstance);
	}

	public static class TestScopeInstance<T> {
		Bean<T> bean;
		CreationalContext<T> ctx;
		T instance;
	}
}
