package de.hilling.junit.cdi.scope;

import javax.inject.Inject;

import org.junit.Test;

import de.hilling.junit.cdi.CdiTestAbstract;

public class TestScopesTestTwo extends CdiTestAbstract {

	@Inject
	private CaseScopedBean caseScopedBean;

	@Inject
	private SuiteScopedBean suiteScopedBean;

	@Test
	public void dummy() {
	}
}
