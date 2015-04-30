package de.hilling.junit.cdi.cucumber.scope;

import javax.inject.Scope;
import java.lang.annotation.*;

/**
 * Instances annotated with {@link ScenarioScoped} have
 * a lifecycle of one scenario.
 * Created to provide the user more transparency of the lifecycle when
 * using {@linkplain de.hilling.junit.cdi.cucumber.CucumberCdi}
 */
@Scope
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface ScenarioScoped {
}
