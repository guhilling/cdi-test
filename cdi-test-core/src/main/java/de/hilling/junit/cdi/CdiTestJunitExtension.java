package de.hilling.junit.cdi;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;

import javax.inject.Inject;
import java.lang.reflect.Field;

public class CdiTestJunitExtension implements BeforeEachCallback, AfterEachCallback {

    static {
        LoggerConfigurator.configure();
    }

    private final InvocationTargetManager invocationTargetManager;
    private final ContextControlWrapper   contextControl = ContextControlWrapper.getInstance();
    private       LifecycleNotifier       lifecycleNotifier;
    private       TestEnvironment         testEnvironment;

    public CdiTestJunitExtension() {
        invocationTargetManager = BeanProvider.getContextualReference(InvocationTargetManager.class, false);
        lifecycleNotifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        lifecycleNotifier.notify(EventType.FINISHING, context);
        contextControl.stopContexts();
        lifecycleNotifier.notify(EventType.FINISHED, context);
        TestContext.deactivate();
        invocationTargetManager.reset();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        testEnvironment = resolveBean(TestEnvironment.class);
        Object testInstance = context.getRequiredTestInstance();
        TestContext.activate(context);
        testEnvironment.setTestInstance(testInstance);
        testEnvironment.setTestMethod(context.getRequiredTestMethod());
        testEnvironment.setTestName(context.getDisplayName());
        invocationTargetManager.addAndActivateTest(testEnvironment.getTestClass());
        lifecycleNotifier.notify(EventType.STARTING, context);
        contextControl.startContexts();
        lifecycleNotifier.notify(EventType.STARTED, context);
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
        ReflectionsUtils.setField(testEnvironment.getTestInstance(), mock, field);
        invocationTargetManager.activateMock(type);
    }
}
