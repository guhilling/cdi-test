package de.hilling.junit.cdi.scope.context;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;
import javax.enterprise.event.Observes;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestScoped;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

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

    protected void activate(@Observes @TestEvent(EventType.STARTING) Object description) {
        active = true;
    }

    protected void deactivate(@Observes @TestEvent(EventType.FINISHING) Object description) {
        getScopeContextHolder().clear();
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
