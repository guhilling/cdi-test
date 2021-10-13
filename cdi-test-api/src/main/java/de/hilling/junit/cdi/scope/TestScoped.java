package de.hilling.junit.cdi.scope;

import jakarta.enterprise.context.NormalScope;
import jakarta.inject.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.immutables.value.Value;

/**
 * Unit test scope valid for for the execution of one test.
 *
 * @see de.hilling.junit.cdi.scope.TestSuiteScoped
 */
@Scope
@NormalScope
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Value.Immutable
public @interface TestScoped {
}
