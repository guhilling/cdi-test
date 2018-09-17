package de.hilling.junit.cdi.scope;

import org.immutables.value.Value;

import javax.enterprise.context.NormalScope;
import javax.inject.Scope;
import java.lang.annotation.*;

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
