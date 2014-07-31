package de.hilling.junit.cdi.scope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Vetoed;

import org.apache.deltaspike.core.util.ProxyUtils;
import org.mockito.Mockito;

@Vetoed
public class MockManager {

	private static final MockManager instance = new MockManager();

	private Map<Class<?>, Object> mocks = new HashMap<>();
	private Map<Class<?>, Set<Class<?>>> activeMocksByTestClass = new HashMap<>();
	private Class<?> activeTest;

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
	public boolean isMockEnabled(Class<?> javaClass) {
		if (activeTest == null) {
			return false;
		} else {
			return currentMockSet().contains(javaClass);
		}
	}

	private Set<Class<?>> currentMockSet() {
		assertTestClassRegistered(activeTest);
		return activeMocksByTestClass.get(activeTest);
	}

	/**
	 * Activate mock for given class.
	 * 
	 * @param mock
	 */
	public void activateMock(Class<?> clazz) {
		mock(clazz);
		if (mocks.containsKey(clazz)) {
			currentMockSet().add(clazz);
		} else {
			throw new IllegalArgumentException("not registered: " + clazz);
		}
	}

	public void addAndActivateTest(Class<?> newTestClass) {
		Class<?> keyClass = ProxyUtils.getUnproxiedClass(newTestClass);
		if (!activeMocksByTestClass.containsKey(keyClass)) {
			activeMocksByTestClass.put(keyClass, new HashSet<Class<?>>());
		}
		this.activeTest = keyClass;
	}

	private void assertTestClassRegistered(Class<?> testToActivate) {
		if (!activeMocksByTestClass.containsKey(testToActivate)) {
			throw new IllegalArgumentException("test class not registered: "
					+ testToActivate);
		}
	}

	public void deactivateTest() {
		activeTest = null;
	}
}
