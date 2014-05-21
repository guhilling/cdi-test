package de.hilling.junit.cdi;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(CdiMockitoRunner.class)
public abstract class CdiTestAbstract {

    private ContextControl contextControl;
    private CdiContainer   cdiContainer;

    @Before
    public void _testSetUp() {
		contextControl.startContexts();
    }

    @After
    public void _testTearDown() {
		contextControl.stopContexts();
    }

    public ContextControl getContextControl() {
        return contextControl;
    }

    public void setContextControl(ContextControl contextControl) {
        this.contextControl = contextControl;
    }

    public CdiContainer getCdiContainer() {
        return cdiContainer;
    }

    public void setCdiContainer(CdiContainer cdiContainer) {
        this.cdiContainer = cdiContainer;
    }

}