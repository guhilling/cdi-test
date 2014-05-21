package de.hilling.junit.cdi.scope;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;

public class TestSuiteContext extends AbstractScopeContext implements Context, Serializable {
	private static final long serialVersionUID = 1L;

	static final Logger LOG = Logger.getLogger(TestSuiteContext.class.getCanonicalName());

	@Override
	public Class<? extends Annotation> getScope() {
		return TestSuiteScope.class;
	}

	@Override
	public boolean isActive() {
		return true;
	}

}
