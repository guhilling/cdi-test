package de.hilling.junit.cdi.scope;

import javax.inject.Scope;
import java.lang.annotation.*;

/**
 * custom scope for cdi unit test suite.
 */
@Scope
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface TestSuiteScoped {
}
