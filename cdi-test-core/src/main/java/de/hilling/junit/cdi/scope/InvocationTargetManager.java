package de.hilling.junit.cdi.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.listeners.MockCreationListener;
import org.mockito.mock.MockCreationSettings;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.lifecycle.TestEvent;

/**
 * Book keeping for mocks. Thread safe.
 */
@BypassTestInterceptor
@TestSuiteScoped
public class InvocationTargetManager implements MockCreationListener {

    private final BeanManager     beanManager;
    private final TestInformation testInformation;

    private final Map<Class<?>, Map<Class<?>, Object>> activeMocksByTestClass        = new HashMap<>();
    private final Map<Class<?>, Set<Class<?>>>         activeAlternativesByTestClass = new HashMap<>();

    @Inject
    public InvocationTargetManager(BeanManager beanManager, TestInformation testInformation) {
        setUpEmptyElementsForNotTestActive();
        this.beanManager = beanManager;
        this.testInformation = testInformation;
    }

    @Override
    public void onMockCreated(Object mock, MockCreationSettings settings) {
        final Class<?> typeToMock = settings.getTypeToMock();
        final Map<Class<?>, Object> mocks = currentMockSet();
        if (mocks.containsKey(typeToMock)) {
            throw new CdiTestException("mock " + typeToMock + " already in set");
        }
        try {
            mocks.put(typeToMock, mock);
        } catch (UnsupportedOperationException uoe) {
            // IGNORE
        }
    }

    @SuppressWarnings("unchecked")
    synchronized <T> T mock(Class<T> javaClass) {
        return (T) currentMockSet().get(javaClass);
    }

    /**
     * Check if mock for the given class is enabled.
     *
     * @param javaClass clazz for which check is performed.
     * @return true if the mock was enabled for this test.
     */
    synchronized boolean isMockEnabled(Class<?> javaClass) {
        return currentMockSet().containsKey(javaClass);
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
            ActivatableTestImplementation activatableTestImplementation = beanManager.getExtension(TestScopeExtension.class)
                    .annotationsFor(alternative);
            for (Class<?> overridden : activatableTestImplementation.value()) {
                if (overridden.equals(javaClass)) {
                    return alternative;
                }
            }
        }
        return null;
    }

    private Map<Class<?>, Object> currentMockSet() {
        return currentElement(activeMocksByTestClass);
    }

    private Set<Class<?>> currentAlternativesSet() {
        return currentElement(activeAlternativesByTestClass);
    }

    private <V> V currentElement(Map<Class<?>, V> classMap) {
        Class<?> activeTest = testInformation.getActiveTest();
        if (activeTest == null) {
            return classMap.get(Object.class);
        } else {
            assertTestClassRegistered(activeTest);
            return classMap.get(activeTest);
        }
    }

    private void setUpEmptyElementsForNotTestActive() {
        activeAlternativesByTestClass.put(Object.class, Collections.emptySet());
        activeMocksByTestClass.put(Object.class, Collections.emptyMap());
    }

    protected synchronized void finished(@Observes @TestEvent(TestState.FINISHING) ExtensionContext testContext) {
        currentMockSet().clear();
        currentAlternativesSet().clear();
        setUpEmptyElementsForNotTestActive();
    }


    public synchronized void activateAlternative(Class<?> alternativeType) {
        currentAlternativesSet().add(alternativeType);
    }

    private void assertTestClassRegistered(Class<?> testToActivate) {
        activeMocksByTestClass.computeIfAbsent(testToActivate, k -> new HashMap<>());
        activeAlternativesByTestClass.computeIfAbsent(testToActivate, k -> new HashSet<>());
    }
}
