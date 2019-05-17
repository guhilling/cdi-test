package de.hilling.junit.cdi;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

/**
 * {@code @CdiTest} is a JUnit Jupiter extension to provide a testing environment for cdi.
 *
 * @see CdiTestJunitExtension
 */
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CdiTestJunitExtension.class)
public @interface CdiTest {
}
