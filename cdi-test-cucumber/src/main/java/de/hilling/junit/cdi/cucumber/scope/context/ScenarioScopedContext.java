package de.hilling.junit.cdi.cucumber.scope.context;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;

import org.junit.runner.Description;

import de.hilling.junit.cdi.cucumber.scope.ScenarioScoped;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.context.AbstractScopeContext;
import de.hilling.junit.cdi.scope.context.CustomScopeInstance;

/**
 * author: fseemann on 29.04.2015.
 */
public class ScenarioScopedContext extends AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ScenarioScopedContext.class
                                                       .getCanonicalName());
    private static final ScenarioScopedContextHolder CONTEXT_HOLDER = new ScenarioScopedContextHolder();
    private static boolean active = false;

    public ScenarioScopedContext() {
    }

    @Override
    protected ScenarioScopedContextHolder getScopeContextHolder() {
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        Bean bean = (Bean) contextual;
        if (getScopeContextHolder().getBeans().containsKey(bean.getBeanClass())) {
            return (T) getScopeContextHolder().getBean(bean.getBeanClass()).instance;
        } else {
            T t = (T) bean.create(creationalContext);
            CustomScopeInstance customInstance = new CustomScopeInstance();
            customInstance.bean = bean;
            customInstance.ctx = creationalContext;
            customInstance.instance = t;
            getScopeContextHolder().putBean(customInstance);
            return t;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Contextual<T> contextual) {
        Bean bean = (Bean) contextual;
        if (getScopeContextHolder().getBeans().containsKey(bean.getBeanClass())) {
            return (T) getScopeContextHolder().getBean(bean.getBeanClass()).instance;
        } else {
            return null;
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

}
