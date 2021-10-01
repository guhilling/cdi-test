package de.hilling.junit.cdi.service;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Test qualifier support for injection into test classes.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface TestQualifier {
}
