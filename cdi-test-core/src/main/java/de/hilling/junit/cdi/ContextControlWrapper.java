package de.hilling.junit.cdi;

import java.util.logging.Logger;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;

import de.hilling.junit.cdi.util.UserLogger;

/**
 * Singleton for booting the container and starting and stopping the standard CDI contexts.
 */
public class ContextControlWrapper {
    private static final Logger LOG = UserLogger.getInstance();

    private final ContextControl contextControl;

    private static final ContextControlWrapper INSTANCE = new ContextControlWrapper();

    /**
     * Returns the singleton.
     * The first invocation of this method will boot the container, if it's not already running.
     *
     * @return the single instance of this class
     */
    public static ContextControlWrapper getInstance() {
        return INSTANCE;
    }

    private ContextControlWrapper() {
        final CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        if (!isCdiContainerBooted(cdiContainer)) {
            LOG.info("booting cdi container");
            long start = System.currentTimeMillis();
            cdiContainer.boot();
            long end = System.currentTimeMillis();
            LOG.info("booting cdi container finished in " + (end - start) + " ms");
        }
        contextControl = cdiContainer.getContextControl();
    }

    private boolean isCdiContainerBooted(final CdiContainer cdiContainer) {
        // For the time being, this simple check will reliably detect, if the container is up and running.
        return cdiContainer.getBeanManager() != null;
    }

    public void startContexts() {
        contextControl.startContexts();
    }

    public void stopContexts() {
        contextControl.stopContexts();
    }

}
