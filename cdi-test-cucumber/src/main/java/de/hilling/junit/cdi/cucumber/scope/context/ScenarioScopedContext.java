package de.hilling.junit.cdi.cucumber.scope.context;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.Context;
import javax.enterprise.event.Observes;

import org.junit.runner.Description;

import de.hilling.junit.cdi.cucumber.scope.ScenarioScoped;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.context.AbstractScopeContext;
import de.hilling.junit.cdi.scope.context.CustomScopeContextHolder;

/**
 * Implements a CDI context for a cucumber scenario.
 */
public class ScenarioScopedContext extends AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;
    private static final CustomScopeContextHolder CONTEXT_HOLDER = new CustomScopeContextHolder();
    private boolean active = false;

    @Override
    protected CustomScopeContextHolder getScopeContextHolder() {
        return CONTEXT_HOLDER;
    }

    @Override public Class<? extends Annotation> getScope() {
        return ScenarioScoped.class;
    }

    protected void activate(@Observes @TestEvent(EventType.STARTING) Description description) {
        active = true;
    }

    protected void deactivate(@Observes @TestEvent(EventType.FINISHING) Description description) {
        getScopeContextHolder().clear();
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

}
