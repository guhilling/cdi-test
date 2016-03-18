package de.hilling.junit.cdi.scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.NormalScope;
import javax.inject.Scope;

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
public @interface TestScoped {
}
