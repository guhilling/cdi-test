package de.hilling.junit.cdi.scope;

import javax.inject.Inject;

import org.junit.Test;

import de.hilling.junit.cdi.CdiTestAbstract;

public abstract class AbstractTestScopesTest extends CdiTestAbstract {

	@Inject
	private CaseScopedBean caseScopedBean;
	@Inject
	private SuiteScopedBean suiteScopedBean;

	@Test
	public void dummy() {
		LOG.info("case: " + caseScopedBean.getIdentifier());
		LOG.info("suite: " + suiteScopedBean.getIdentifier());
	}

}