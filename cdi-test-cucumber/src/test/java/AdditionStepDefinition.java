import static org.junit.Assert.assertTrue;

import javax.enterprise.context.ApplicationScoped;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definition without package.
 * author: fseemann on 13.04.2015.
 */
@ApplicationScoped
public class AdditionStepDefinition {
    Integer augend;
    Integer addend;
    Integer sum;

    @Given("^two integers with value 1$")
    public void two_integers_with_value()
    throws Throwable {
        augend = 1;
        addend = 1;
    }

    @When("^those integers get added$")
    public void those_integers_get_added()
    throws Throwable {
        sum = augend + addend;
    }

    @Then("^the result should be 2$")
    public void the_result_should_be()
    throws Throwable {
        assertTrue(sum == 2);
    }
}
