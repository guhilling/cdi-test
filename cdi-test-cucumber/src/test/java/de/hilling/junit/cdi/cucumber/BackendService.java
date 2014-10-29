package de.hilling.junit.cdi.cucumber;

import javax.annotation.PostConstruct;

public class BackendService {
    private int balance;

    @PostConstruct
    public void postConstruct() {
        System.out.println("post");
    }

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
