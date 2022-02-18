package de.hilling.junit.cdi.scope.context;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.Context;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

/**
 * Implementation of context for {@link TestSuiteScoped} beans.
 *
 * @see TestSuiteScoped
 */
@BypassTestInterceptor
public class TestSuiteContext extends AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;
    private static final CustomScopeContextHolder CONTEXT_HOLDER = new CustomScopeContextHolder();

    @Override
    public Class<? extends Annotation> getScope() {
        return TestSuiteScoped.class;
    }

    @Override
    protected CustomScopeContextHolder getScopeContextHolder() {
        return CONTEXT_HOLDER;
    }
}
