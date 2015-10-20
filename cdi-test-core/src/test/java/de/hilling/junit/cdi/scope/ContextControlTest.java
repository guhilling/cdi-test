package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.scopedbeans.ApplicationScopedBean;
import de.hilling.junit.cdi.scopedbeans.RequestScopedBean;
import de.hilling.junit.cdi.scopedbeans.ScopedBean;
import de.hilling.junit.cdi.scopedbeans.SessionScopedBean;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.UUID;

@RunWith(CdiUnitRunner.class)
public class ContextControlTest {

    @Inject
    private RequestScopedBean requestScopedBean;
    @Inject
    private ApplicationScopedBean applicationScopedBean;
    @Inject
    private SessionScopedBean sessionScopedBean;

    @Inject
    private ContextControl contextControl;

    @Test
    public void restartRequestn() {
        runTest(requestScopedBean, RequestScoped.class);
    }

    @Test
    public void restartApplication() {
        runTest(applicationScopedBean, ApplicationScoped.class);
    }

    private void runTest(ScopedBean scopedBean, Class<? extends Annotation> scope) {
        contextControl.stopContext(scope);
        contextControl.startContext(scope);
        UUID uuid = scopedBean.getUuid();
        contextControl.stopContext(scope);
        contextControl.startContext(scope);
        UUID uuid2 = scopedBean.getUuid();
        Assert.assertNotEquals(uuid, uuid2);
    }
}
