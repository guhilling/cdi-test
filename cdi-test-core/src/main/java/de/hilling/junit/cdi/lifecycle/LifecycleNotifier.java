package de.hilling.junit.cdi.lifecycle;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

/**
 * Used for notifications for the test and injection phases.
 *
 * @see TestState
 */
@BypassTestInterceptor
@TestSuiteScoped
public class LifecycleNotifier {

    @Inject
    @Any
    private Event<Object> lifecycleEvent;

    public void notify(final TestState testCaseLifecycle, Object description) {
        final ImmutableTestEvent.Builder anntationBuilder = ImmutableTestEvent.builder();
        lifecycleEvent.select(anntationBuilder.value(testCaseLifecycle).build()).fire(description);
    }
}
