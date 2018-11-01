package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
public abstract class AbstractTestScopesTest {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTestScopesTest.class);

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