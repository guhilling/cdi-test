package de.hilling.junit.cdi;

import java.util.HashMap;
import java.util.Map;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class CdiMockitoRunner extends BlockJUnit4ClassRunner {

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

	@SuppressWarnings("unchecked")
	protected <T> T resolveTest(Class<T> clazz) {
		if (testCases.containsKey(clazz)) {
			return (T) testCases.get(clazz);
		} else {
			T testCase = BeanProvider.getContextualReference(clazz, false);
			if (testCase instanceof CdiTestAbstract) {
				CdiTestAbstract cdiTestAbstract = (CdiTestAbstract) testCase;
				cdiTestAbstract.setContextControl(contextControl);
				cdiTestAbstract.setCdiContainer(cdiContainer);
			}
			testCases.put(clazz, testCase);
			return testCase;
		}
	}

}
