package de.hilling.junit.cdi.cucumber;

public class BankingBackendService {
    private int balance;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

}
