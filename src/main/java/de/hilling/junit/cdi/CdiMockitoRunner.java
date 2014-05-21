package de.hilling.junit.cdi;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class CdiMockitoRunner extends BlockJUnit4ClassRunner {
	private static final Logger LOG = Logger.getLogger(CdiMockitoRunner.class.getCanonicalName());

	public static final CdiContainer cdiContainer;
	public static final ContextControl contextControl;
	public static final Map<Class<?>, Object> testCases = new HashMap<>();

	static {
		cdiContainer = CdiContainerLoader.getCdiContainer();
		cdiContainer.boot();
		contextControl = cdiContainer.getContextControl();
	}

	public CdiMockitoRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected Object createTest() {
		final Class<?> testClass = getTestClass().getJavaClass();
		return resolveTest(testClass);
	}

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		LOG.fine("starting " + method.getName());
		contextControl.startContexts();
		super.runChild(method, notifier);
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
