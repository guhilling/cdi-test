package de.hilling.junit.cdi;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class CdiMockitoRunner
extends BlockJUnit4ClassRunner {

    public static final CdiContainer cdiContainer;
    public static final ContextControl contextControl;

    static {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        contextControl = cdiContainer.getContextControl();
    }

    public CdiMockitoRunner(Class<?> klass)
    throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() {
        final Class<?> testClass = getTestClass().getJavaClass();
        Object testCase = resolve(testClass);
        if (testCase instanceof CdiTestAbstract) {
            CdiTestAbstract cdiTestAbstract = (CdiTestAbstract) testCase;
            cdiTestAbstract.setContextControl(contextControl);
            cdiTestAbstract.setCdiContainer(cdiContainer);
        }
        return testCase;
    }

    protected <T> T resolve(Class<T> clazz) {
        return BeanProvider.getContextualReference(clazz, false);
    }

}
