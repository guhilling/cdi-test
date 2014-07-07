package de.hilling.junit.cdi.scope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mockito.Mockito;

import de.hilling.junit.cdi.proxy.ProxyMethodHandler;

public class MockManager {

	private static final MockManager instance = new MockManager();

	private Map<Class<?>, Object> activeMocks = new HashMap<>();
	private Map<Object, Set<ProxyMethodHandler<?>>> handlerMap = new HashMap<>();

	private MockManager() {
	}

	@SuppressWarnings("unchecked")
	public <T> T mock(Class<T> javaClass) {
		if (!activeMocks.containsKey(javaClass)) {
			activeMocks.put(javaClass, Mockito.mock(javaClass));
		}
		return (T) activeMocks.get(javaClass);
	}

	public static MockManager getInstance() {
		return instance;
	}

	public void resetMocks() {
		Mockito.reset(activeMocks.values().toArray());
	}

	public void registerProxyMethodHandler(
			ProxyMethodHandler<Object> handler) {
		Object mock = handler.getMock();
		if(!handlerMap.containsKey(mock)) {
			handlerMap.put(mock, new HashSet<ProxyMethodHandler<?>>());
		}
		handlerMap.get(mock).add(handler);
	}

	public void activateProxyMocks(Object mock) {
		for (ProxyMethodHandler<?> handler : handlerMap.get(mock)) {
			handler.setMockEnabled(true);
		}
	}
	
	public void deactivateAllProxyMocks() {
		for (Set<ProxyMethodHandler<?>> handlerSet : handlerMap.values()) {
			for (ProxyMethodHandler<?> handler : handlerSet) {
				handler.setMockEnabled(false);
			}
		}
	}
}
