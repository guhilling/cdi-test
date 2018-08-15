package de.hilling.junit.cdi.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runners.model.InitializationError;

import java.io.IOException;

/**
 * Run cucumber tests with cdi injection.
 */
public class CucumberCdi extends Cucumber {
    /**
     * Constructor called by JUnit.
     *
     * @param clazz the class with the @RunWith annotation.
     * @throws java.io.IOException                         if there is a problem
     * @throws org.junit.runners.model.InitializationError if there is another problem
     */
    public CucumberCdi(Class clazz) throws InitializationError, IOException {
        super(clazz);
    }
}
