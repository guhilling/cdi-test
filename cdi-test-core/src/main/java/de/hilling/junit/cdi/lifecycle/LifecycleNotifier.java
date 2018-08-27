package de.hilling.junit.cdi.lifecycle;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class LifecycleNotifier {

    @Inject
    @Any
    private Event<Object> lifecycleEvent;

    public void notify(final EventType testCaseLifecycle, Object description) {
        lifecycleEvent.select(new TestEvent__Literal(testCaseLifecycle)).fire(description);
    }
}
