package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import org.junit.jupiter.api.extension.ExtensionContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@TestSuiteScoped
public class TestTransactionManager {

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;

}
