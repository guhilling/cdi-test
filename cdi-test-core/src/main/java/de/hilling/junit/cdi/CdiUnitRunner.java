package de.hilling.junit.cdi;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Runner for cdi tests providing: <ul> <li>test creation via cdi using deltaspike</li> <li>injection and activation of
 * mocks using MockManager</li> </ul>
 */
public class CdiUnitRunner extends BlockJUnit4ClassRunner {
    private static final Logger LOG = Logger.getLogger(CdiUnitRunner.class
            .getCanonicalName());

    private final InvocationTargetManager invocationTargetManager;
    private final ContextControl contextControl = ContextControlWrapper.getInstance();

    private static Map<Class<?>, Object> testCases = new HashMap<>();

    private LifecycleNotifier lifecycleNotifier;

    static {
        LoggerConfigurator.configure();
    }

    private CurrentTestInformation testInformation;

    public CdiUnitRunner(Class<?> klass) throws InitializationError {
        super(klass);
        invocationTargetManager = BeanProvider.getContextualReference(InvocationTargetManager.class, false);
        lifecycleNotifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
        testInformation = resolveBean(CurrentTestInformation.class);
    }

    @Override
    protected Object createTest() {
        final Class<?> testClass = getTestClass().getJavaClass();
        Object test = resolveTest(testClass);
        for (Field field : ReflectionsUtils.getAllFields(test.getClass())) {
            if (field.isAnnotationPresent(Mock.class)) {
                assignMockAndActivateProxy(field, test);
            }
            if (isTestActivatable(field)) {
                activateForTest(field);
            }
        }
        return test;
    }

    private boolean isTestActivatable(Field field) {
        Class type = field.getType();
        if (type.isAnnotationPresent(ActivatableTestImplementation.class)) {
            return true;
        }
        return false;
    }

    private void activateForTest(Field field) {
        invocationTargetManager.activateAlternative(field.getType());
    }

    private void assignMockAndActivateProxy(Field field, Object test) {
        field.setAccessible(true);
        try {
            Class<?> type = field.getType();
            Object mock = invocationTargetManager.mock(type);
            field.set(test, mock);
            invocationTargetManager.activateMock(type);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        final Description description = describeChild(method);
        LOG.fine("> preparing " + description);
        invocationTargetManager.addAndActivateTest(description.getTestClass());
        testInformation.setMethod(method.getMethod());
        testInformation.setTestClass(description.getTestClass());
        contextControl.startContexts();
        lifecycleNotifier.notify(EventType.STARTING, description);
        LOG.fine(">> starting " + description);
        super.runChild(method, notifier);
        LOG.fine("<< finishing " + description);
        lifecycleNotifier.notify(EventType.FINISHING, description);
        contextControl.stopContexts();
        lifecycleNotifier.notify(EventType.FINISHED, description);
        invocationTargetManager.reset();
        LOG.fine("< finished " + description);
    }

    @SuppressWarnings("unchecked")
    protected <T> T resolveTest(Class<T> clazz) {
        if (!testCases.containsKey(clazz)) {
            testCases.put(clazz, resolveBean(clazz));
        }
        return (T) testCases.get(clazz);
    }

    private <T> T resolveBean(Class<T> clazz) {
        return BeanProvider.getContextualReference(clazz, false);
    }
}
