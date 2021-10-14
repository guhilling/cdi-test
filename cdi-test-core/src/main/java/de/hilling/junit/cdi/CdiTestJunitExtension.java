package de.hilling.junit.cdi;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.InvocationTargetManager;
import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import de.hilling.junit.cdi.util.ReflectionsUtils;

import org.junit.jupiter.api.extension.*;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.inject.Qualifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

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
        for (Field field : ReflectionsUtils.getAllFields(testInstance.getClass())) {
            if (field.isAnnotationPresent(Inject.class)) {
                Optional<Annotation> qualifier = Arrays.stream(field.getDeclaredAnnotations()).filter(this::isQualifier).findFirst();
                setField(testInstance, field, qualifier);
            }
        }
    }

    private boolean isQualifier(Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(Qualifier.class);
    }

    private void setField(Object testInstance, Field field, Optional<Annotation> qualifierAnnotation) {
        Object bean;
        if(qualifierAnnotation.isPresent()) {
            bean = contextControl.getContextualReference(field.getType(), qualifierAnnotation.get());
        } else {
            bean = contextControl.getContextualReference(field.getType());
        }
        ReflectionsUtils.setField(testInstance, bean, field);
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

    private boolean isTestActivatable(Field field) {
        Class<?> type = field.getType();
        return type.isAnnotationPresent(ActivatableTestImplementation.class);
    }

}
