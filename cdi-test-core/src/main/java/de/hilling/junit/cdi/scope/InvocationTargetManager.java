package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import org.apache.deltaspike.core.util.ProxyUtils;
import org.mockito.Mockito;
import org.mockito.listeners.MockCreationListener;
import org.mockito.mock.MockCreationSettings;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.*;

/**
 * Book keeping for mocks. Thread safe.
 */
@BypassTestInterceptor
@TestSuiteScoped
public class InvocationTargetManager implements MockCreationListener {

    @Inject
    private BeanManager beanManager;

    private Map<Class<?>, Map<Class<?>, Object>> activeMocksByTestClass = new HashMap<>();
    private Map<Class<?>, Set<Class<?>>> activeAlternativesByTestClass = new HashMap<>();
    private Class<?> activeTest;

    @Override
    public void onMockCreated(Object mock, MockCreationSettings settings) {
        final Class typeToMock = settings.getTypeToMock();
        final Map<Class<?>, Object> mocks = currentMockSet();
        if (mocks.containsKey(typeToMock)) {
            System.out.println("settings: " + settings.toString());
            throw new RuntimeException("mock " + typeToMock + " already in set");
        }
        mocks.put(typeToMock, mock);
    }

    @SuppressWarnings("unchecked")
    synchronized <T> T mock(Class<T> javaClass) {
        return (T) currentMockSet().get(javaClass);
    }

    /**
     * Reset all {@link Mockito} mocks. See {@link Mockito#reset(Object...)}
     */
    public synchronized void tearDownTest() {
        activeTest = null;
    }

    /**
     * Check if mock for the given class is enabled.
     *
     * @param javaClass clazz for which check is performed.
     * @return true if the mock was enabled for this test.
     */
    synchronized boolean isMockEnabled(Class<?> javaClass) {
        return currentMockSet().keySet().contains(javaClass);
    }

    /**
     * Check if alternative for the given class is enabled.
     *
     * @param javaClass clazz for which check is performed.
     * @return true if {@link #activateAlternative} was called before.
     */
    public synchronized boolean isAlternativeEnabled(Class<?> javaClass) {
        return alternativeFor(javaClass) != null;
    }

    public Class<?> alternativeFor(Class<?> javaClass) {
        for (Class<?> alternative : currentAlternativesSet()) {
            AnnotatedType type = beanManager.getExtension(TestScopeExtension.class)
                                            .decoratedTypeFor(alternative);
            if (type != null) {
                ActivatableTestImplementation activatableTestImplementation = type.getAnnotation(
                        ActivatableTestImplementation.class);
                for (Class<?> overridden : activatableTestImplementation.value()) {
                    if (overridden.equals(javaClass)) {
                        return alternative;
                    }
                }
            }
        }
        return null;
    }

    private Map<Class<?>, Object> currentMockSet() {
        if (activeTest == null) {
            throw new IllegalStateException("no test active");
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

    public synchronized void activateAlternative(Class<?> alternativeType) {
        currentAlternativesSet().add(alternativeType);
    }

    public synchronized void activateTest(Class<?> newTestClass) {
        Class<?> keyClass = ProxyUtils.getUnproxiedClass(newTestClass);
        this.activeTest = keyClass;
        activeMocksByTestClass.put(keyClass, new HashMap<>());
        activeAlternativesByTestClass.put(keyClass, new HashSet<>());
    }

    private void assertTestClassRegistered(Class<?> testToActivate) {
        if (!activeMocksByTestClass.containsKey(testToActivate)) {
            throw new IllegalArgumentException("test class not registered: " + testToActivate);
        }
    }
}
