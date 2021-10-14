package de.hilling.junit.cdi;

import jakarta.enterprise.inject.spi.Bean;

import static java.util.logging.Level.INFO;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.logging.Logger;

import org.jboss.weld.construction.api.WeldCreationalContext;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.manager.api.WeldManager;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.util.UserLogger;

/**
 * Singleton for booting the container and starting and stopping the standard CDI contexts.
 */
@BypassTestInterceptor
public class ContextControlWrapper {
    private static final Logger LOG = UserLogger.getInstance();

    private static final ContextControlWrapper INSTANCE = new ContextControlWrapper();
    private final Weld weld;
    private final WeldContainer weldContainer;
    private final WeldManager weldManager;
    private ContextControl contextControl;

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
        weld = new Weld();
        LOG.info("booting cdi container");
        long start = System.currentTimeMillis();
        weldContainer = weld.initialize();
        long end = System.currentTimeMillis();
        LOG.log(INFO, "booting cdi container finished in {0} ms", end - start);
        if(!weldContainer.isRunning()) {
            throw new CdiTestException("couldn't start weld");
        }
        weldManager = (WeldManager) weldContainer.getBeanManager();
        contextControl = getContextualReference(ContextControl.class);
    }

    @SuppressWarnings({ "unchecked" })
    public <T> T getContextualReference(Class<T> beanType, Annotation ... qualifiers) {
        WeldCreationalContext<T> creationalContext = weldManager.createCreationalContext(null);
        Set<Bean<?>> beans = weldManager.getBeans(beanType, qualifiers);
        Bean<T> bean = (Bean<T>) weldManager.resolve(beans);
        return (T) weldManager.getReference(bean, beanType, creationalContext);
    }

    public void startContexts() {
        contextControl.startContexts();
    }

    public void stopContexts() {
        contextControl.stopContexts();
    }

}
