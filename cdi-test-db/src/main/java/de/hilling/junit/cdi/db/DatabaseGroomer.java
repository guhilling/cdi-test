package de.hilling.junit.cdi.db;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.runner.Description;

import javax.enterprise.event.Observes;

@TestSuiteScoped
public class DatabaseGroomer {

    public void beforeTestStarts(@Observes @TestEvent(EventType.STARTING) Description testDescription) {
        System.out.println("starting " + testDescription);
    }
    public void beforeTestFinishes(@Observes @TestEvent(EventType.FINISHING) Description testDescription) {
        System.out.println("finishing " + testDescription);
    }
}
