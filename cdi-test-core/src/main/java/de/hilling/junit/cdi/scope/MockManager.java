package de.hilling.junit.cdi.scope;

import org.apache.deltaspike.core.util.ProxyUtils;
import org.mockito.Mockito;

import javax.enterprise.inject.Vetoed;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Vetoed
public class MockManager {

    private static final MockManager instance = new MockManager();

    private Map<Class<?>, Object> mocks = new HashMap<>();
    private Map<Class<?>, Set<Class<?>>> activeMocksByTestClass = new HashMap<>();
    private Class<?> activeTest;

    private MockManager() {
    }

    public static MockManager getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T mock(Class<T> javaClass) {
        if (!mocks.containsKey(javaClass)) {
            mocks.put(javaClass, Mockito.mock(javaClass));
        }
        return (T) mocks.get(javaClass);
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
     * @param javaClass clazz for which check is performed.
     * @return true if {@link #activateMock} was called before.
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
     * @param clazz class to activate mock for
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
