package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiTestAbstract;
import org.junit.Test;

import javax.inject.Inject;

public abstract class AbstractTestScopesTest extends CdiTestAbstract {

    @Inject
    private CaseScopedBean caseScopedBean;
    @Inject
    private SuiteScopedBean suiteScopedBean;

    @Test
    public void showInfo() {
        LOG.info("case: " + caseScopedBean.getIdentifier());
        LOG.info("suite: " + suiteScopedBean.getIdentifier());
    }

}