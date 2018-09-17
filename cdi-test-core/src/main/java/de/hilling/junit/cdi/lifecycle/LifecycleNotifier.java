package de.hilling.junit.cdi.lifecycle;

import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@TestSuiteScoped
public class LifecycleNotifier {

    @Inject
    @Any
    private Event<Object> lifecycleEvent;

    public void notify(final EventType testCaseLifecycle, Object description) {
        final ImmutableTestEvent.Builder anntationBuilder = ImmutableTestEvent.builder();
        lifecycleEvent.select(anntationBuilder.value(testCaseLifecycle).build()).fire(description);
    }
}
