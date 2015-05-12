package de.hilling.junit.cdi.annotations;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Stereotype
@Alternative
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Priority(1000)
/**
 * Use this annotation to mark Alternatives that should globally replace
 * production implementations.
 * <p>
 *     These services cannot be disabled or enabled on a per test basis
 *     because the container is only started once.
 * </p>
 */
public @interface TestImplementation {
}
