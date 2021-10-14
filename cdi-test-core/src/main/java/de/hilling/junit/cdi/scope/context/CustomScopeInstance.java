package de.hilling.junit.cdi.scope.context;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.Bean;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import org.immutables.value.Value;

@BypassTestInterceptor
@Value.Immutable
public interface CustomScopeInstance<T> {
    Bean<T> getBean();

    CreationalContext<T> getCtx();

    T getInstance();
}
