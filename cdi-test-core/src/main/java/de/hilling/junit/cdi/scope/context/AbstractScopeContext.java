package de.hilling.junit.cdi.scope.context;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public abstract class AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(AbstractScopeContext.class.getCanonicalName());

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Contextual<T> contextual) {
        Bean<T> bean = (Bean<T>) contextual;
        if (getScopeContextHolder().getBeans().containsKey(bean.getBeanClass())) {
            return (T) getScopeContextHolder().getBean(bean.getBeanClass()).instance;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
        Bean<T> bean = (Bean<T>) contextual;
        if (getScopeContextHolder().getBeans().containsKey(bean.getBeanClass())) {
            return (T) getScopeContextHolder().getBean(bean.getBeanClass()).instance;
        } else {
            return createNewInstance(creationalContext, bean);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createNewInstance(final CreationalContext<T> creationalContext, Bean<T> bean) {
        LOG.fine("creating new bean of type " + bean.getBeanClass());
        T t = bean.create(creationalContext);
        getScopeContextHolder().putBean(new CustomScopeInstance(bean, creationalContext, t));
        return t;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    protected abstract ScopeContextHolder getScopeContextHolder();

}