package de.hilling.junit.cdi.scope;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
