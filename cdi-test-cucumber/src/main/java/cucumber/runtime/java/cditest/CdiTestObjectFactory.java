package cucumber.runtime.java.cditest;

import cucumber.runtime.java.ObjectFactory;
import de.hilling.junit.cdi.ContextControlWrapper;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class CdiTestObjectFactory implements ObjectFactory {
    private static final Logger LOG = Logger.getLogger(CdiTestObjectFactory.class.getCanonicalName());

    private ContextControl contextControl;
    private LifecycleNotifier notifier;

    {
        contextControl = ContextControlWrapper.getInstance();
        notifier = BeanProvider.getContextualReference(LifecycleNotifier.class, false);
    }

    private Map<Class, Object> definitions = new HashMap<>();

    @Override
    public void start() {
        LOG.info("starting");
        contextControl.startContexts();
        notifier.notify(EventType.STARTING, Description.createSuiteDescription("cucumber"));
    }

    @Override
    public void stop() {
        contextControl.stopContexts();
        notifier.notify(EventType.FINISHING, Description.createSuiteDescription("cucumber"));
        LOG.info("stopped");
    }

    @Override
    public void addClass(Class<?> clazz) {
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        if (definitions.get(clazz) == null) {
            LOG.info("adding " + clazz);
            definitions.put(clazz, BeanProvider.getContextualReference(clazz, false));
        }
        return (T) definitions.get(clazz);
    }
}
