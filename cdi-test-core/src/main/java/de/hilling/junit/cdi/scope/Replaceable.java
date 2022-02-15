package de.hilling.junit.cdi.scope;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

import org.immutables.value.Value;

/**
 * Marker interface used internally by the {@link de.hilling.junit.cdi.scope.TestScopeExtension} to
 * mark classes that can be mocked or replaced by other test beans.
 */
@Inherited
@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Value.Immutable
public @interface Replaceable {
}
