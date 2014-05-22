package de.hilling.junit.cdi.scope;

import java.util.logging.Logger;

@TestScoped
public class CaseScopedBean extends IdentifiablePojo {

	private static final Logger LOG = Logger.getLogger(CaseScopedBean.class.getCanonicalName());

	public CaseScopedBean() {
		LOG.info("created");
	}
}
