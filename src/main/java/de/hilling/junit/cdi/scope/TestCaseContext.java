package de.hilling.junit.cdi.scope;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.Context;

public class TestCaseContext extends AbstractScopeContext implements Context, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends Annotation> getScope() {
		return TestCaseScope.class;
	}

}
