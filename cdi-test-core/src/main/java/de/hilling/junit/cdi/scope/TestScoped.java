package de.hilling.junit.cdi.scope;

import javax.enterprise.context.NormalScope;
import javax.inject.Scope;
import java.lang.annotation.*;

/**
 * custom scope for cdi unit test.
 */
@Scope
@NormalScope
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface TestScoped {
}
