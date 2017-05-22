package de.hilling.junit.cdi.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.apache.deltaspike.core.util.ProxyUtils;
import org.mockito.Mockito;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

/**
 * Book keeping for mocks. Thread safe.
 */
@BypassTestInterceptor
@TestSuiteScoped
public class InvocationTargetManager {

    @Inject
    private BeanManager beanManager;

    private Map<Class<?>, Object>        mocks                         = new HashMap<>();
    private Map<Class<?>, Set<Class<?>>> activeMocksByTestClass        = new HashMap<>();
    private Map<Class<?>, Set<Class<?>>> activeAlternativesByTestClass = new HashMap<>();
    private Class<?> activeTest;

    @SuppressWarnings("unchecked")
    public synchronized <T> T mock(Class<T> javaClass) {
        if (!mocks.containsKey(javaClass)) {
            mocks.put(javaClass, Mockito.mock(javaClass));
        }
        return (T) mocks.get(javaClass);
    }

    /**
     * Reset all {@link Mockito} mocks. See {@link Mockito#reset(Object...)}
     */
    public synchronized void reset() {
        Mockito.reset(mocks.values().toArray());
        activeTest = null;
    }

    /**
     * Check if mock for the given class is enabled.
     *
     * @param javaClass clazz for which check is performed.
     *
     * @return true if {@link #activateMock} was called before.
     */
    public synchronized boolean isMockEnabled(Class<?> javaClass) {
        return currentMockSet().contains(javaClass);
    }

    /**
     * Check if alternative for the given class is enabled.
     *
     * @param javaClass clazz for which check is performed.
     *
     * @return true if {@link #activateAlternative} was called before.
     */
    public synchronized boolean isAlternativeEnabled(Class<?> javaClass) {
        return alternativeFor(javaClass) != null;
    }

    public Class<?> alternativeFor(Class<?> javaClass) {
        for (Class<?> alternative : currentAlternativesSet()) {
            AnnotatedType type = beanManager.getExtension(TestScopeExtension.class).decoratedTypeFor(alternative);
            if (type != null) {
                ActivatableTestImplementation activatableTestImplementation = type.getAnnotation(
                ActivatableTestImplementation.class);
                for (Class<?> overriden : activatableTestImplementation.value()) {
                    if (overriden.equals(javaClass)) {
                        return alternative;
                    }
                }
            }
        }
        return null;
    }

    private Set<Class<?>> currentMockSet() {
        if (activeTest == null) {
            return Collections.emptySet();
        }
        assertTestClassRegistered(activeTest);
        return activeMocksByTestClass.get(activeTest);
    }

    private Set<Class<?>> currentAlternativesSet() {
        if (activeTest == null) {
            return Collections.emptySet();
        }
        assertTestClassRegistered(activeTest);
        return activeAlternativesByTestClass.get(activeTest);
    }

    /**
     * Activate mock for given class.
     *
     * @param clazz class to activate mock for
     *
     * @return mock
     */
    public synchronized Object activateMock(Class<?> clazz) {
        if (activeTest == null) {
            throw new IllegalArgumentException("not test active: " + clazz);
        }
        final Object mock = mock(clazz);
        if (mocks.containsKey(clazz)) {
            currentMockSet().add(clazz);
        } else {
            throw new IllegalArgumentException("not registered: " + clazz);
        }
        return mock;
    }

    public synchronized void activateAlternative(Class<?> alternativeType) {
        currentAlternativesSet().add(alternativeType);
    }

    public synchronized void addAndActivateTest(Class<?> newTestClass) {
        Class<?> keyClass = ProxyUtils.getUnproxiedClass(newTestClass);
        if (!activeMocksByTestClass.containsKey(keyClass)) {
            activeMocksByTestClass.put(keyClass, new HashSet<Class<?>>());
        }
        if (!activeAlternativesByTestClass.containsKey(keyClass)) {
            activeAlternativesByTestClass.put(keyClass, new HashSet<Class<?>>());
        }
        this.activeTest = keyClass;
    }

    private void assertTestClassRegistered(Class<?> testToActivate) {
        if (!activeMocksByTestClass.containsKey(testToActivate)) {
            throw new IllegalArgumentException("test class not registered: " + testToActivate);
        }
    }
}
