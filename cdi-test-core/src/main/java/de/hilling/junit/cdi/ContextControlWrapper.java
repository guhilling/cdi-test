package de.hilling.junit.cdi;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;

import javax.enterprise.inject.Alternative;
import java.lang.annotation.Annotation;

/**
 * Wrapper for handling cdi startup, shutdown and lifecycle.
 */
@Alternative
public class ContextControlWrapper implements ContextControl {
    private CdiContainer cdiContainer;
    private ContextControl contextControl;

    private static final ContextControlWrapper INSTANCE = new ContextControlWrapper();

    public static ContextControl getInstance() {
        return INSTANCE;
    }

    private ContextControlWrapper() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        contextControl = cdiContainer.getContextControl();
    }

    @Override
    public void startContexts() {
        contextControl.startContexts();
    }

    @Override
    public void stopContexts() {
        contextControl.stopContexts();
    }

    @Override
    public void startContext(Class<? extends Annotation> scopeClass) {
        contextControl.startContext(scopeClass);
    }

    @Override
    public void stopContext(Class<? extends Annotation> scopeClass) {
        contextControl.stopContext(scopeClass);
    }
}
