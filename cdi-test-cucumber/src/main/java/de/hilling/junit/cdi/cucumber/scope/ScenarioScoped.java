package de.hilling.junit.cdi.cucumber.scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * Instances annotated with {@link ScenarioScoped} have
 * a lifecycle of one scenario.
 * Created to provide the user more transparency of the lifecycle when
 * using {@linkplain de.hilling.junit.cdi.cucumber.CucumberCdi}
 * <p/>
 * author: fseemann on 29.04.2015.
 */
@Scope
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface ScenarioScoped {
}
