package de.hilling.junit.cdi.scope.context;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

/**
 * Container with necessary information to destroy cached beans.
 */
@BypassTestInterceptor
public class CustomScopeInstance<T> {
    public Bean<T> bean;
    public CreationalContext<T> ctx;
    public T instance;
}
