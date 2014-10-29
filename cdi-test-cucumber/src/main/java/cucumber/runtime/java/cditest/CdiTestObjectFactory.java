package cucumber.runtime.java.cditest;

import cucumber.runtime.java.ObjectFactory;
import de.hilling.junit.cdi.CdiContainerWrapper;
import de.hilling.junit.cdi.lifecycle.LifecycleNotifier;
import de.hilling.junit.cdi.scope.EventType;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class CdiTestObjectFactory implements ObjectFactory {
    private static final Logger LOG = Logger.getLogger(CdiTestObjectFactory.class.getCanonicalName());

    private CdiContainer cdiContainer;
    private ContextControl contextControl;
    private LifecycleNotifier notifier;

    {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        contextControl = cdiContainer.createContextControl();
        notifier = BeanProvider.getContextualReference(
                LifecycleNotifier.class, false);
    }

    static {
        CdiContainerWrapper.startCdiContainer();
    }

    private Map<Class, Object> definitions = new HashMap<>();

    @Override
    public void start() {
        LOG.info("starting");
        notifier.notify(EventType.STARTING, Description.createSuiteDescription("cucumber"));
        contextControl.startContexts();
    }

    @Override
    public void stop() {
        contextControl.stopContexts();
        notifier.notify(EventType.FINISHING, Description.createSuiteDescription("cucumber"));
        LOG.info("stopped");
    }

    @Override
    public void addClass(Class<?> clazz) {
        LOG.info("adding " + clazz);
        definitions.put(clazz, BeanProvider.getContextualReference(clazz, false));
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return (T) definitions.get(clazz);
    }
}
