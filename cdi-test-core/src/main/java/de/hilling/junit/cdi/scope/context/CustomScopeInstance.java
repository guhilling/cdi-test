package de.hilling.junit.cdi.scope.context;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

/**
 * Container with necessary information to destroy cached beans.
 * <p/>
 * author: fseemann on 29.04.2015.
 */
public class CustomScopeInstance<T> {
    public Bean<T> bean;
    public CreationalContext<T> ctx;
    public T instance;
}
