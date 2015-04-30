package de.hilling.junit.cdi.cucumber.scope;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * author: fseemann on 29.04.2015.
 */
@ScenarioScoped
public class ScenarioScopeStepDefinition {
    private Boolean aBoolean;

    @Given("^a boolean$")
    public void a_boolean()
    throws Throwable {
        aBoolean = Boolean.FALSE;
    }

    @When("^I set the boolean to true$")
    public void I_set_the_boolean_to_true()
    throws Throwable {
        aBoolean = Boolean.TRUE;
    }

    @Then("^the boolean should be true$")
    public void the_boolean_should_be_true()
    throws Throwable {
        assertThat(aBoolean, is(true));
    }

    @Then("^the boolean should be null$")
    public void the_boolean_should_be_null()
    throws Throwable {
        assertThat(aBoolean, is(nullValue()));
    }
}
