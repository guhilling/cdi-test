package de.hilling.junit.cdi.cucumber;

import javax.inject.Inject;

public class BankService {

    @Inject
    private BackendService service;

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
