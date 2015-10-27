package de.hilling.junit.cdi.annotations;

import de.hilling.junit.cdi.scope.TestScoped;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TestScoped
@Stereotype
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Use this annotation to mark Alternatives that can be enabled per test class.
 */
public @interface ActivatableTestImplementation {
    /**
     * Enumerates the classes and/or interfaces that should be replaced by the injected bean.
     * @return
     */
    Class<?>[] value() default {};
}
