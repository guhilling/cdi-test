package de.hilling.junit.cdi.scope;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.enterprise.util.AnnotationLiteral;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestScopeExtension implements Extension, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TestScopeExtension.class
            .getCanonicalName());

    /**
     * add contexts after bean discovery.
     *
     * @param afterBeanDiscovery
     * @param beanManager
     */
    public void afterBeanDiscovery(
            @Observes AfterBeanDiscovery afterBeanDiscovery,
            BeanManager beanManager) {
        addContexts(afterBeanDiscovery);
    }

    private void addContexts(AfterBeanDiscovery abd) {
        abd.addContext(new TestSuiteContext());
        abd.addContext(new TestContext());
    }

    public <X> void processBean(@Observes ProcessAnnotatedType<X> pat) {
        AnnotatedType<X> type = pat.getAnnotatedType();
        Class<X> javaClass = type.getJavaClass();
        if (shouldProxyCdiType(javaClass)) {
            AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>();
            builder.readFromType(type);
            builder.addToClass(new AnnotationLiteral<Mockable>() {
                private static final long serialVersionUID = 1L;
            });
            try {
                pat.setAnnotatedType(builder.create());
            } catch (RuntimeException e) {
                LOG.log(Level.SEVERE, "unable to process type " + pat, e);
            }
        }
    }

    private <X> boolean shouldProxyCdiType(Class<X> javaClass) {
        return !javaClass.isAnonymousClass()
                && javaClass.getName().startsWith("de.hilling");
    }

}