package de.hilling.junit.cdi.scope.context;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.enterprise.context.spi.Context;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public class TestSuiteContext extends AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;
    private static final TestScopeContextHolder CONTEXT_HOLDER = new TestScopeContextHolder();

    @Override
    public Class<? extends Annotation> getScope() {
        return TestSuiteScoped.class;
    }

    @Override
    protected TestScopeContextHolder getScopeContextHolder() {
        return CONTEXT_HOLDER;
    }
}
