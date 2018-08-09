package toplevelpackage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdditionStepDefinition {
    Integer augend;
    Integer addend;
    Integer sum;

    @Given("^two integers with value 1$")
    public void two_integers_with_value() {
        augend = 1;
        addend = 1;
    }

    @When("^those integers get added$")
    public void those_integers_get_added() {
        sum = augend + addend;
    }

    @Then("^the result should be 2$")
    public void the_result_should_be() {
        assertTrue(sum == 2);
    }
}
