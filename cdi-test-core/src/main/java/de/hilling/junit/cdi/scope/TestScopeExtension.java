package de.hilling.junit.cdi.scope;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.enterprise.util.AnnotationLiteral;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CDI {@link javax.enterprise.inject.spi.Extension} to enable proxying of (nearly) all method
 * invocations.
 * <p>
 *     By default, these are all classes, except:
 *     <ul>
 *         <li>Anonymous classes.</li>
 *         <li>Enums.</li>
 *     </ul>
 *     To preventing <em>everything</em> from being proxied it is possible to define explicit
 *     packages.
 * </p>
 */
public class TestScopeExtension implements Extension, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TestScopeExtension.class
            .getCanonicalName());

    /**
     * Add contexts after bean discovery.
     *
     * @param afterBeanDiscovery
     * @param beanManager
     */
    public void afterBeanDiscovery(
            @Observes AfterBeanDiscovery afterBeanDiscovery,
            BeanManager beanManager) {
        afterBeanDiscovery.addContext(new TestSuiteContext());
        afterBeanDiscovery.addContext(new TestContext());
    }

    public <X> void processBean(@Observes ProcessAnnotatedType<X> pat) {
        AnnotatedType<X> type = pat.getAnnotatedType();
        Class<X> javaClass = type.getJavaClass();
        if (shouldProxyCdiType(javaClass)) {
            AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<>();
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
        if(javaClass.isAnonymousClass()) {
            return false;
        }
        if(!javaClass.getName().startsWith("de.hilling")) {
            return false;
        }
        return !javaClass.isEnum();
    }

}