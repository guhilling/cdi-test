package de.hilling.junit.cdi.scope;

import java.util.logging.Logger;

@TestSuiteScoped
public class CaseScopedBean {

	private static final Logger LOG = Logger.getLogger(CaseScopedBean.class.getCanonicalName());

	public CaseScopedBean() {
		LOG.info("created");
	}
}
