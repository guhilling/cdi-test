package de.hilling.junit.cdi;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;

public class CdiTestJunitExtension implements BeforeEachCallback, AfterEachCallback {

    private static final Logger LOG = Logger.getLogger(CdiTestJunitExtension.class.getCanonicalName());

    static {
        LoggerConfigurator.configure();
    }

    private final InvocationTargetManager invocationTargetManager;
    private final ContextControlWrapper   contextControl = ContextControlWrapper.getInstance();
    private       LifecycleNotifier       lifecycleNotifier;
    private       TestContext             testContext;

    public CdiTestJunitExtension() {
        invocationTargetManager = BeanProvider.getContextualReference(InvocationTargetManager.class, false);
        lifecycleNotifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        lifecycleNotifier.notify(EventType.FINISHING, context);
        contextControl.stopContexts();
        lifecycleNotifier.notify(EventType.FINISHED, context);
        invocationTargetManager.reset();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        testContext = resolveBean(TestContext.class);
        Object testInstance = context.getRequiredTestInstance();
        testContext.setTestInstance(testInstance);
        testContext.setTestMethod(context.getRequiredTestMethod());
        testContext.setTestName(context.getDisplayName());
        invocationTargetManager.addAndActivateTest(testContext.getTestClass());
        contextControl.startContexts();
        lifecycleNotifier.notify(EventType.STARTING, context);
        for (Field field : ReflectionsUtils.getAllFields(testInstance.getClass())) {
            if (field.isAnnotationPresent(Mock.class)) {
                assignMockAndActivateProxy(field);
            }
            if (field.isAnnotationPresent(Inject.class)) {
                ReflectionsUtils.setField(testInstance, resolveBean(field.getType()), field);
            }
            if (isTestActivatable(field)) {
                activateForTest(field);
            }
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

    private void assignMockAndActivateProxy(Field field) {
        Class<?> type = field.getType();
        Object mock = invocationTargetManager.mock(type);
        ReflectionsUtils.setField(testContext.getTestInstance(), mock, field);
        invocationTargetManager.activateMock(type);
    }
}