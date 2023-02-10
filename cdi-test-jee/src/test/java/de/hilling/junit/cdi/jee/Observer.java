package de.hilling.junit.cdi.jee;

import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class Observer {
    private static final Logger LOG = LoggerFactory.getLogger(Observer.class);

    public void transactionScopeActivated(
    @Observes @Initialized(TransactionScoped.class) final Transaction transaction) {
        LOG.info("transaction started: {}", transaction);
    }

}
