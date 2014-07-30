package de.hilling.junit.cdi.scope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Vetoed;

import org.mockito.Mockito;

@Vetoed
public class MockManager {

	private static final MockManager instance = new MockManager();

	private Map<Class<?>, Object> mocks = new HashMap<>();
	private Set<Class<?>> activeMocks = new HashSet<>();

	private MockManager() {
	}

	@SuppressWarnings("unchecked")
	public <T> T mock(Class<T> javaClass) {
		if (!mocks.containsKey(javaClass)) {
			mocks.put(javaClass, Mockito.mock(javaClass));
		}
		return (T) mocks.get(javaClass);
	}

	public static MockManager getInstance() {
		return instance;
	}

	/**
	 * Deactivate all mocks. Call before new test case starts.
	 */
	public void clearActivations() {
		activeMocks.clear();
	}

	/**
	 * Reset all {@link Mockito} mocks. See {@link Mockito#reset(Object...)}
	 */
	public void resetMocks() {
		Mockito.reset(mocks.values().toArray());
	}

	/**
	 * Check if mock for the given class is enabled.
	 * 
	 * @param javaClass
	 * @return true if {@link #activateMock(Object)} was called before.
	 */
	public boolean isEnabled(Class<?> javaClass) {
		return activeMocks.contains(javaClass);
	}

	/**
	 * Activate mock for given class.
	 * 
	 * @param mock
	 */
	public void activateMock(Class<?> clazz) {
		if (mocks.containsKey(clazz)) {
			activeMocks.add(clazz);
		} else {
			throw new IllegalArgumentException("not registered: " + clazz);
		}
	}

}
