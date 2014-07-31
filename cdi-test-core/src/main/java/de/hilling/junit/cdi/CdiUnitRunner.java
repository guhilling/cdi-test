package de.hilling.junit.cdi;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;

import de.hilling.junit.cdi.scope.MockManager;
import de.hilling.junit.cdi.scope.TestLifecycle;

public class CdiUnitRunner extends BlockJUnit4ClassRunner {
	private static final Logger LOG = Logger.getLogger(CdiUnitRunner.class
			.getCanonicalName());

	private static CdiContainer cdiContainer;
	private static ContextControl contextControl;
	private static Map<Class<?>, Object> testCases = new HashMap<>();

	private LifecycleNotifier lifecycleNotifier;
	private MockManager mockManager = MockManager.getInstance();

	static {
		configureLogger();
		startCdiContainer();
	}

	private static void startCdiContainer() {
		cdiContainer = CdiContainerLoader.getCdiContainer();
		cdiContainer.boot();
		contextControl = cdiContainer.getContextControl();
	}

	public CdiUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		lifecycleNotifier = BeanProvider.getContextualReference(
				LifecycleNotifier.class, false);
	}

	private static void configureLogger() {
		try (InputStream inputStream = CdiUnitRunner.class
				.getResourceAsStream("/logging.properties")) {
			if (inputStream == null) {
				warnLoggerNotConfigured("file not found");
			} else {
				LogManager logManager = LogManager.getLogManager();
				logManager.readConfiguration(inputStream);
			}
		} catch (final IOException e) {
			warnLoggerNotConfigured(e.getMessage());
		}
	}

	private static void warnLoggerNotConfigured(String message) {
		Logger.getAnonymousLogger().severe(
				"Could not load default logging.properties file");
		Logger.getAnonymousLogger().severe(message);
	}

	@Override
	protected Object createTest() {
		final Class<?> testClass = getTestClass().getJavaClass();
		Object test = resolveTest(testClass);
		assignMocks(test);
		return test;
	}

	private void assignMocks(Object test) {
		for (Field field : ReflectionsUtils.getAllFields(test.getClass())) {
			if (field.isAnnotationPresent(Mock.class)) {
				assignMockAndActivateProxy(field, test);
			}
		}
	}

	private void assignMockAndActivateProxy(Field field, Object test) {
		final boolean accessible = field.isAccessible();
		if (!accessible) {
			field.setAccessible(true);
		}
		try {
			Class<?> type = field.getType();
			Object mock = mockManager.mock(type);
			field.set(test, mock);
			mockManager.activateMock(type);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			if (!accessible) {
				field.setAccessible(false);
			}
		}
	}

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		LOG.fine("starting " + method.getName());
		// activate mocks based on test case (per thread?)
		contextControl.startContexts();
		lifecycleNotifier.notify(TestLifecycle.TEST_STARTS);
		mockManager.resetMocks();
		super.runChild(method, notifier);
		lifecycleNotifier.notify(TestLifecycle.TEST_FINISHED);
		// deactivate mocks
		contextControl.stopContexts();
		LOG.fine("finished " + method.getName());
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
