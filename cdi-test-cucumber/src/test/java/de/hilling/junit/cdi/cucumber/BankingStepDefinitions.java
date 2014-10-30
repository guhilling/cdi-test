package de.hilling.junit.cdi.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BankingStepDefinitions {

    @Inject
    private BankService bank;

    @Given("^a User has no money in their account$")
    public void init() {
    }

    @When("^£(\\d+) is deposited in to the account$")
    public void deposit(int amount) {
        bank.deposit(amount);
    }

    @When("^£(\\d+) is withdrawn from the account$")
    public void withdraw(int amount) {
        bank.withdraw(amount);
    }

    @Then("^the balance should be £(-*\\d+)$")
    public void balanceEquals(int amount) {
        Assert.assertEquals(amount, bank.getBalance());
    }

    @Then("^an exception should be thrown$")
    public void throwException() {

    }
}