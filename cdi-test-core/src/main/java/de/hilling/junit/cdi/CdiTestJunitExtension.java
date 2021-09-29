package de.hilling.junit.cdi;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.jupiter.api.extension.*;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.lang.reflect.Field;

public class CdiTestJunitExtension implements TestInstancePostProcessor, BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    static {
        LoggerConfigurator.configure();
    }

    private final InvocationTargetManager invocationTargetManager;
    private final ContextControlWrapper contextControl = ContextControlWrapper.getInstance();

    private LifecycleNotifier lifecycleNotifier;
    private TestEnvironment testEnvironment;

    public CdiTestJunitExtension() {
        invocationTargetManager = BeanProvider.getContextualReference(InvocationTargetManager.class, false);
        lifecycleNotifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        Mockito.framework()
               .addListener(invocationTargetManager);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        Mockito.framework()
               .removeListener(invocationTargetManager);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        testEnvironment = resolveBean(TestEnvironment.class);
        testEnvironment.setTestInstance(testInstance);
        for (Field field : ReflectionsUtils.getAllFields(testInstance.getClass())) {
            if (field.isAnnotationPresent(Inject.class)) {
                ReflectionsUtils.setField(testInstance, resolveBean(field.getType()), field);
            }
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        TestContext.activate();
        testEnvironment.setTestMethod(context.getRequiredTestMethod());
        testEnvironment.setTestName(context.getDisplayName());
        lifecycleNotifier.notify(TestState.STARTING, context);
        contextControl.startContexts();
        lifecycleNotifier.notify(TestState.STARTED, context);
        for (Field field : ReflectionsUtils.getAllFields(testEnvironment.getTestClass())) {
            if (isTestActivatable(field)) {
                invocationTargetManager.activateAlternative(field.getType());
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        lifecycleNotifier.notify(TestState.FINISHING, context);
        contextControl.stopContexts();
        lifecycleNotifier.notify(TestState.FINISHED, context);
        TestContext.deactivate();
    }

    private <T> T resolveBean(Class<T> clazz) {
        return BeanProvider.getContextualReference(clazz, false);
    }

    private boolean isTestActivatable(Field field) {
        Class<?> type = field.getType();
        return type.isAnnotationPresent(ActivatableTestImplementation.class);
    }

}
