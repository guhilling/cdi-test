package de.hilling.junit.cdi.scope;

import java.util.logging.Logger;

@TestSuiteScoped
public class SuiteScopedBean extends IdentifiablePojo {

    private static final Logger LOG = Logger.getLogger(SuiteScopedBean.class.getCanonicalName());

    public SuiteScopedBean() {
        LOG.info("created");
    }
}
