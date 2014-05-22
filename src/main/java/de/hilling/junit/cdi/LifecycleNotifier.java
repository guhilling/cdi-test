package de.hilling.junit.cdi;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hilling.junit.cdi.scope.TestCaseLifecycle;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class LifecycleNotifier {

	@Inject
	private Event<TestCaseLifecycle> lifecycleEvent;

	public void notify(TestCaseLifecycle testCaseLifecycle) {
		lifecycleEvent.fire(testCaseLifecycle);
	}
}
