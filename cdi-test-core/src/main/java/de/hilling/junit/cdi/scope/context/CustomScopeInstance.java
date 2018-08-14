package de.hilling.junit.cdi.scope.context;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

/**
 * Container with necessary information to destroy cached beans.
 */
@BypassTestInterceptor
public class CustomScopeInstance<T> {
    public final Bean<T> bean;
    public final CreationalContext<T> ctx;
    public final T instance;

    public CustomScopeInstance(Bean<T> bean, CreationalContext<T> ctx, T instance) {
        this.bean = bean;
        this.ctx = ctx;
        this.instance = instance;
    }
}
