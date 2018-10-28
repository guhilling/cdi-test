package de.hilling.junit.cdi.microprofile;


import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines an overriding property value used in cdi unit tests.
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
@Repeatable(ConfigPropertyValues.class)
public @interface ConfigPropertyValue {

    String name();

    String value();
}
