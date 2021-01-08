package de.hilling.junit.cdi.testing;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE, METHOD, FIELD, CONSTRUCTOR})
@Retention(RUNTIME)
public @interface Resource {
}
