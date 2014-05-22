package de.hilling.junit.cdi;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.hilling.junit.cdi.scope.TestLifecycle;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class LifecycleNotifier {

	@Inject
	private Event<TestLifecycle> lifecycleEvent;

	public void notify(TestLifecycle testCaseLifecycle) {
		lifecycleEvent.fire(testCaseLifecycle);
	}
}
