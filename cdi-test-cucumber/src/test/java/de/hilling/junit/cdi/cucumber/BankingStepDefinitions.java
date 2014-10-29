package de.hilling.junit.cdi.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.hilling.junit.cdi.scope.TestScoped;
import org.junit.Assert;

import javax.inject.Inject;

@TestScoped
public class BankingStepDefinitions {

    @Inject
    private BankService bank;

    @Given("^a User has no money in their account$")
    public void a_User_has_no_money_in_their_current_account() {
    }

    @When("^£(\\d+) is deposited in to the account$")
    public void deposit(int amount) {
        bank.deposit(amount);
    }

    @When("^£(\\d+) is withdrawn from the account$")
    public void withdraw(int amount) {
        bank.withdraw(amount);
    }

    @Then("^the balance should be £(\\d+)$")
    public void balanceEquals(int amount) {
        Assert.assertEquals(amount, bank.getBalance());
    }

    @Then("^an exception should be thrown$")
    public void throwException() {

    }
}