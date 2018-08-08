package de.hilling.junit.cdi;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;

public class CdiTestJunitExtension implements BeforeEachCallback, AfterEachCallback {

    private static final Logger LOG = Logger.getLogger(CdiTestJunitExtension.class.getCanonicalName());
    private static Map<Class<?>, Object> testCases = new HashMap<>();

    static {
        LoggerConfigurator.configure();
    }

    private final InvocationTargetManager invocationTargetManager;
    private final ContextControlWrapper   contextControl = ContextControlWrapper.getInstance();
    private LifecycleNotifier lifecycleNotifier;
    private Object testInstance;

    public CdiTestJunitExtension() {
        invocationTargetManager = BeanProvider.getContextualReference(InvocationTargetManager.class, false);
        lifecycleNotifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        contextControl.stopContexts();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        testInstance = context.getRequiredTestInstance();
        contextControl.startContexts();
        for (Field field : ReflectionsUtils.getAllFields(testInstance.getClass())) {
            if (field.isAnnotationPresent(Mock.class)) {
                assignMockAndActivateProxy(field, testInstance);
            }
            if (field.isAnnotationPresent(Inject.class)) {
                resolveAndAssignBean(field);
            }
            if (isTestActivatable(field)) {
                activateForTest(field);
            }
        }
        LOG.log(Level.FINE, "running class " + testInstance.getClass().getSimpleName());
    }

    private void resolveAndAssignBean(Field field) {
        field.setAccessible(true);
        try {
            Class<?> type = field.getType();
            Object bean = resolveBean(type);
            field.set(testInstance, bean);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CdiTestException("error activating proxy", e);
        } finally {
            field.setAccessible(false);
        }
    }

    private <T> T resolveBean(Class<T> clazz) {
        return BeanProvider.getContextualReference(clazz, false);
    }

    private boolean isTestActivatable(Field field) {
        Class type = field.getType();
        return type.isAnnotationPresent(ActivatableTestImplementation.class);
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
            throw new CdiTestException("error activating proxy", e);
        } finally {
            field.setAccessible(false);
        }
    }
}
