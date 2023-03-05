package de.hilling.junit.cdi;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.jboss.weld.proxy.WeldClientProxy;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.Mockito;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;

/**
 * JUnit 5 extension for cdi lifecycle management and injection into test cases. Detailed documentation available at <a
 * href="https://cdi-test.hilling.de">Github Pages</a>
 * <p>
 * {@link Mockito} will be automatically added to the lifecycle so the {@link Mockito} JUnit Extension should <em>not</em> be added to the test
 * additionally.
 * </p>
 */
public class CdiTestJunitExtension implements TestInstancePostProcessor, BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    static {
        LoggerConfigurator.configure();
    }

    private final InvocationTargetManager invocationTargetManager;
    private final ContextControlWrapper   contextControl = ContextControlWrapper.getInstance();

    private final LifecycleNotifier lifecycleNotifier;
    private       TestEnvironment   testEnvironment;

    public CdiTestJunitExtension() {
        invocationTargetManager = contextControl.getContextualReference(InvocationTargetManager.class);
        lifecycleNotifier = contextControl.getContextualReference(LifecycleNotifier.class);
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
        testEnvironment = contextControl.getContextualReference(TestEnvironment.class);
        testEnvironment.setTestInstance(testInstance);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        TestContext.activate();
        testEnvironment.setTestMethod(context.getRequiredTestMethod());
        testEnvironment.setTestName(context.getDisplayName());
        lifecycleNotifier.notify(TestState.STARTING, context);
        contextControl.startContexts();
        Object cdiInstance = contextControl.getContextualReference(testEnvironment.getTestClass());
        if(cdiInstance instanceof WeldClientProxy) {
            testEnvironment.setCdiInstance(((WeldClientProxy)cdiInstance).getMetadata().getContextualInstance());
        } else {
            testEnvironment.setCdiInstance(cdiInstance);
        }
        for (Field field : ReflectionsUtils.getAllFields(testEnvironment.getTestClass())) {
            if (copyInjectedField(field)) {
                copyField(field);
            }
        }
        for (Field field : ReflectionsUtils.getAllFields(testEnvironment.getTestClass())) {
            if (isTestActivatable(field)) {
                invocationTargetManager.activateAlternative(field.getType());
            }
        }
        lifecycleNotifier.notify(TestState.STARTED, context);
    }

    private boolean copyInjectedField(Field field) {
        return INJECTION_ANNOTATIONS.stream().anyMatch(field::isAnnotationPresent);
    }

    private static final List<Class<? extends Annotation>> INJECTION_ANNOTATIONS = Arrays.asList(
        Inject.class,
        PersistenceContext.class,
        PersistenceUnit.class
    );

    private void copyField(Field field) {
        Object testInstance = testEnvironment.getTestInstance();
        Object cdiInstance = testEnvironment.getCdiInstance();
        ReflectionsUtils.copyField(cdiInstance, testInstance, field);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        lifecycleNotifier.notify(TestState.FINISHING, context);
        contextControl.stopContexts();
        lifecycleNotifier.notify(TestState.FINISHED, context);
        TestContext.deactivate();
    }

    private boolean isTestActivatable(Field field) {
        Class<?> type = field.getType();
        return type.isAnnotationPresent(ActivatableTestImplementation.class);
    }

}
