package de.hilling.junit.cdi.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

/**
 * Wrapper für Javassist-Proxy.
 */
public class ProxyProducer {

	@SuppressWarnings("unchecked")
	public <T> T createProxy(final Class<T> _clazz, final MethodHandler handler) {
		Class clazz;
 		ProxyFactory factory = new ProxyFactory();
		String name = _clazz.getCanonicalName();
		if(name.contains("$")) {
			String plainName = name.substring(0, name.indexOf("$"));
			try {
				clazz = Class.forName(plainName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			clazz = _clazz;
		}
		if (clazz.isInterface()) {
			factory.setInterfaces(new Class[] { clazz });
		} else {
			factory.setSuperclass(clazz);
		}
		factory.setFilter(new MethodFilter() {
			public boolean isHandled(Method m) {
				// ignore finalize()
				return !m.getName().equals("finalize");
			}
		});
		Class<?> c = factory.createClass();
		T foo;
		try {
			foo = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		((Proxy) foo).setHandler(handler);
		return foo;
	}

}
