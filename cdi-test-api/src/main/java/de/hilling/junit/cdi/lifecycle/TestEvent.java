package de.hilling.junit.cdi.lifecycle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import de.hilling.junit.cdi.scope.EventType;
import de.hilling.lang.annotations.GenerateLiteral;

@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@GenerateLiteral
public @interface TestEvent {
    EventType value();
}
