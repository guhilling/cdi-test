package de.hilling.junit.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

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
        contextControl.startContext(ApplicationScoped.class);
        contextControl.startContext(SessionScoped.class);
        contextControl.startContext(RequestScoped.class);
    }

    @After
    public void _testTearDown() {
        contextControl.stopContext(RequestScoped.class);
        contextControl.stopContext(SessionScoped.class);
        contextControl.stopContext(ApplicationScoped.class);
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