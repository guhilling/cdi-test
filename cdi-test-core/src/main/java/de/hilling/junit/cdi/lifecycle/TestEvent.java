package de.hilling.junit.cdi.lifecycle;

import de.hilling.junit.cdi.scope.EventType;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestEvent {
    EventType value();
}
