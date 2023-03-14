package de.hilling.junit.cdi.jee.jta;

import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionScoped;

import java.util.logging.Logger;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.arjuna.ats.jta.cdi.DelegatingTransactionManager;

import de.hilling.junit.cdi.scope.TestScoped;

@TestScoped
public class TestTransactionObserver {
    private static final Logger LOG = Logger.getLogger(TestTransactionObserver.class.getCanonicalName());
    void transactionScopeActivated(@Observes @Initialized(TransactionScoped.class) final Object event) {
        logEvent("TX initialized", event);
    }

    void transactionScopeDestroyed(@Observes @Destroyed(TransactionScoped.class) final Object event) {
        logEvent("TX destroyed", event);
    }

    private void logEvent(String txAction, Object event) {
        if(event instanceof TransactionImple) {
            TransactionImple tx = (TransactionImple) event;
            LOG.info(String.format("%s %s", txAction, tx.get_uid()));
        } else if (event instanceof DelegatingTransactionManager) {
            DelegatingTransactionManager txm = (DelegatingTransactionManager) event;
            try {
                LOG.info(String.format("%s %s", txAction, txm.getTransaction()));
            } catch (SystemException e) {
                LOG.info(txAction + " " + txm);
            }
        } else {
            LOG.info(txAction);
        }
    }

}
