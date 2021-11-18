package de.hilling.junit.cdi.scope;

import jakarta.inject.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.immutables.value.Value;

/**
 * Unit test scope valid for the execution of all unit tests.
 *
 * <p>
 *     This scope is useful for the creation of factory objects.
 *     These are usually only needed once but might be expensive to create.
 * </p>
 *
 *
 * @see de.hilling.junit.cdi.scope.TestScoped
 */
@Scope
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Value.Immutable
public @interface TestSuiteScoped {
}
