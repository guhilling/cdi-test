package cucumber.runtime.java.cditest;

import cucumber.runtime.java.ObjectFactory;
import de.hilling.junit.cdi.ContextControlWrapper;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class CdiTestObjectFactory implements ObjectFactory {
    private static final Logger LOG = Logger.getLogger(CdiTestObjectFactory.class.getCanonicalName());

    private ContextControlWrapper contextControl = ContextControlWrapper.getInstance();
    private LifecycleNotifier notifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
    private Map<Class, Object> definitions = new HashMap<>();

    @Override
    public void start() {
        LOG.info("starting");
        contextControl.startContexts();
        notifier.notify(EventType.STARTED, Description.createSuiteDescription("cucumber"));
    }

    @Override
    public void stop() {
        contextControl.stopContexts();
        notifier.notify(EventType.FINISHING, Description.createSuiteDescription("cucumber"));
        LOG.info("stopped");
    }

    @Override
    public void addClass(Class<?> clazz) {
        // no-op, as in default implementation.
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return (T) definitions.computeIfAbsent(clazz, c -> BeanProvider.getContextualReference(c, false));
    }
}
