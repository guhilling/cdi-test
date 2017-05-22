package de.hilling.junit.cdi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Do not activate the configurable test interceptor for this bean.
 * <p>
 * This is especially useful for beans belonging to the test framework itself.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BypassTestInterceptor {
}
