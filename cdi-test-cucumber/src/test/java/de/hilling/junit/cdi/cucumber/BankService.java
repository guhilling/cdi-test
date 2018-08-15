package de.hilling.junit.cdi.cucumber;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class BankService {

    @Inject
    private BankingBackendService service;

    public void deposit(int amount) {
        service.deposit(amount);
    }

    public void withdraw(int amount) {
        service.withdraw(amount);
    }

    public int getBalance() {
        return service.getBalance();
    }

}
