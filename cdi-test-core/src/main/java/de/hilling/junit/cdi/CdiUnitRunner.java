package de.hilling.junit.cdi;

import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.util.LoggerConfigurator;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Runner for cdi tests providing:
 * <ul>
 * <li>test creation via cdi using deltaspike</li>
 * <li>injection and activation of mocks using MockManager</li>
 * </ul>
 */
public class CdiUnitRunner extends BlockJUnit4ClassRunner {
    private static final Logger LOG = Logger.getLogger(CdiUnitRunner.class
            .getCanonicalName());

    private static Map<Class<?>, Object> testCases = new HashMap<>();

    private LifecycleNotifier lifecycleNotifier;

    static {
        LoggerConfigurator.configure();
        CdiContainerWrapper.startCdiContainer();
    }

    public CdiUnitRunner(Class<?> klass) throws InitializationError {
        super(klass);
        lifecycleNotifier = BeanProvider.getContextualReference(
                LifecycleNotifier.class, false);
    }

    @Override
    protected Object createTest() {
        final Class<?> testClass = getTestClass().getJavaClass();
        Object test = resolveTest(testClass);
        CdiContainerWrapper.assignMocks(test);
        return test;
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        final Description description = describeChild(method);
        LOG.fine("> preparing " + description);
        CdiContainerWrapper.mockManager.addAndActivateTest(description.getTestClass());
        CdiContainerWrapper.mockManager.resetMocks();
        CdiContainerWrapper.contextControl.startContexts();
        lifecycleNotifier.notify(EventType.STARTING, description);
        LOG.fine(">> starting " + description);
        super.runChild(method, notifier);
        LOG.fine("<< finishing " + description);
        lifecycleNotifier.notify(EventType.FINISHING, description);
        CdiContainerWrapper.contextControl.stopContexts();
        CdiContainerWrapper.mockManager.deactivateTest();
        LOG.fine("< finished " + description);
    }

    @SuppressWarnings("unchecked")
    protected <T> T resolveTest(Class<T> clazz) {
        if (testCases.containsKey(clazz)) {
            return (T) testCases.get(clazz);
        } else {

            T testCase = BeanProvider.getContextualReference(clazz, false);
            testCases.put(clazz, testCase);
            return testCase;
        }
    }

}
