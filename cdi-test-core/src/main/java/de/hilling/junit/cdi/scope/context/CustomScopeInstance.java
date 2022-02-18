package de.hilling.junit.cdi.scope.context;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import org.immutables.value.Value;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

/**
 * Generate an instance of custom scope.
 * @param <T> scope type.
 */
@BypassTestInterceptor
@Value.Immutable
public interface CustomScopeInstance<T> {
    Bean<T> getBean();

    CreationalContext<T> getCtx();

    T getInstance();
}
