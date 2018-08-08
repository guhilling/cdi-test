package de.hilling.junit.cdi.lifecycle;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class LifecycleNotifier {

    @Inject
    @Any
    private Event<Object> lifecycleEvent;

    public void notify(final EventType testCaseLifecycle, Object description) {
        AnnotationLiteral<TestEvent> event = new TestEventLiteral() {
            @Override
            public EventType value() {
                return testCaseLifecycle;
            }
        };
        lifecycleEvent.select(event).fire(description);
    }

    private static abstract class TestEventLiteral extends AnnotationLiteral<TestEvent> implements TestEvent {
    }
}
