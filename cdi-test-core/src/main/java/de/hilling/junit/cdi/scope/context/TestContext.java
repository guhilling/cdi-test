package de.hilling.junit.cdi.scope.context;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.scope.TestScoped;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.enterprise.context.spi.Context;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

@BypassTestInterceptor
@TestSuiteScoped
public class TestContext extends AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TestContext.class.getCanonicalName());
    private static final CustomScopeContextHolder CONTEXT_HOLDER = new CustomScopeContextHolder();

    private static boolean active = false;

    public TestContext() {
        LOG.fine("created");
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return TestScoped.class;
    }

    public static void activate() {
        active = true;
    }

    public static void deactivate() {
        CONTEXT_HOLDER.clear();
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    protected CustomScopeContextHolder getScopeContextHolder() {
        return CONTEXT_HOLDER;
    }

}
