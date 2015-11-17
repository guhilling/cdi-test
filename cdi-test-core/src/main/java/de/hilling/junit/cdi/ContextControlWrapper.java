package de.hilling.junit.cdi;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.Alternative;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;

/**
 * Wrapper for handling cdi startup, shutdown and lifecycle.
 */
@Alternative
public class ContextControlWrapper implements ContextControl {
    private ContextControl contextControl;

    private static final ContextControlWrapper INSTANCE = new ContextControlWrapper();

    public static ContextControl getInstance() {
        return INSTANCE;
    }

    private ContextControlWrapper() {
        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
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
