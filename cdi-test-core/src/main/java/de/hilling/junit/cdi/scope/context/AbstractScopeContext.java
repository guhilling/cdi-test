package de.hilling.junit.cdi.scope.context;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import java.io.Serializable;
import java.util.logging.Logger;

public abstract class AbstractScopeContext implements Context, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(AbstractScopeContext.class.getCanonicalName());

    @Override
    public <T> T get(final Contextual<T> contextual) {
        Bean<T> bean = (Bean<T>) contextual;
        if (getScopeContextHolder().getBeans()
                                   .containsKey(bean.getBeanClass())) {
            return getInstanceFromScope(bean);
        } else {
            return null;
        }
    }

    @Override
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
        Bean<T> bean = (Bean<T>) contextual;
        if (getScopeContextHolder().getBeans()
                                   .containsKey(bean.getBeanClass())) {
            return getInstanceFromScope(bean);
        } else {
            return createNewInstance(creationalContext, bean);
        }
    }

    @SuppressWarnings("unchecked")
    private  <T> T getInstanceFromScope(Bean<T> bean) {
        return (T) getScopeContextHolder().getBean(bean.getBeanClass())
                                          .getInstance();
    }

    @SuppressWarnings("unchecked")
    private <T> T createNewInstance(final CreationalContext<T> creationalContext, Bean<T> bean) {
        LOG.fine("creating new bean of type " + bean.getBeanClass());
        T instance = bean.create(creationalContext);
        ImmutableCustomScopeInstance.Builder<T> builder = ImmutableCustomScopeInstance.builder();
        builder.bean(bean)
               .ctx(creationalContext)
               .instance(instance);
        getScopeContextHolder().putBean(builder.build());
        return instance;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    protected abstract ScopeContextHolder getScopeContextHolder();

}