package de.hilling.junit.cdi.scope;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;
import javax.enterprise.event.Observes;

import de.hilling.junit.cdi.scope.context.AbstractScopeContext;
import de.hilling.junit.cdi.scope.context.TestScopeContextHolder;

@TestSuiteScoped
public class TestCaseContext extends AbstractScopeContext implements Context, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(TestCaseContext.class.getCanonicalName());
	private static final TestScopeContextHolder CONTEXT_HOLDER = new TestScopeContextHolder();

	private static boolean active = false;

	public TestCaseContext() {
		LOG.fine("created");
	}

	@Override
	public Class<? extends Annotation> getScope() {
		return TestCaseScoped.class;
	}

	protected void lifecycle(@Observes TestCaseLifecycle testCaseLifecycle) {
		switch (testCaseLifecycle) {
		case TEST_STARTS:
			active = true;
			break;
		case TEST_FINISHED:
			getScopeContextHolder().clear();
			active = false;
			break;
		default:
			throw new IllegalStateException("unhandled state " + testCaseLifecycle);
		}
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	protected TestScopeContextHolder getScopeContextHolder() {
		return CONTEXT_HOLDER;
	}

}
