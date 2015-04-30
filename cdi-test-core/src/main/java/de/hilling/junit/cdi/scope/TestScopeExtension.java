package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.scope.context.TestSuiteContext;
import de.hilling.junit.cdi.scope.annotationreplacement.AnnotatedTypeAdapter;
import de.hilling.junit.cdi.scope.annotationreplacement.AnnotationReplacementAdapter;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CDI {@link javax.enterprise.inject.spi.Extension} to enable proxying of (nearly) all method invocations. <p> By
 * default, these are all classes, except: <ul> <li>Anonymous classes.</li> <li>Enums.</li> </ul> To preventing
 * <em>everything</em> from being proxied it is possible to define explicit packages.
 */
public class TestScopeExtension implements Extension, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TestScopeExtension.class
            .getCanonicalName());
    private final Map<Class<? extends Annotation>, Annotation> replacementMap = new HashMap<>();

    /**
     * Add contexts after bean discovery.
     *
     * @param afterBeanDiscovery AfterBeanDiscovery
     */
    public void afterBeanDiscovery(
            @Observes AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery.addContext(new TestSuiteContext());
        afterBeanDiscovery.addContext(new TestContext());
    }

    public <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
        LOG.log(Level.FINE, "processing type " + pat);
        AnnotatedTypeAdapter<T> enhancedAnnotatedType = new AnnotationReplacementAdapter<>(pat.getAnnotatedType(),
                Collections.unmodifiableMap(replacementMap));
        pat.setAnnotatedType(enhancedAnnotatedType);
    }


    public <X> void processBean(@Observes ProcessAnnotatedType<X> pat) {
        AnnotatedType<X> type = pat.getAnnotatedType();
        Class<X> javaClass = type.getJavaClass();
        if (ReflectionsUtils.isTestClass(javaClass)) {
            AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<>();
            builder.readFromType(type);
            builder.addToClass(new AnnotationLiteral<TestSuiteScoped>() {
                private static final long serialVersionUID = 1L;
            });
            try {
                pat.setAnnotatedType(builder.create());
            } catch (RuntimeException e) {
                LOG.log(Level.SEVERE, "unable to process type " + pat, e);
            }
        } else if (ReflectionsUtils.shouldProxyCdiType(javaClass)) {
            AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<>();
            builder.readFromType(type);
            builder.addToClass(new AnnotationLiteral<Mockable>() {
                private static final long serialVersionUID = 1L;
            });
            try {
                {
                    pat.setAnnotatedType(builder.create());
                }
                pat.setAnnotatedType(builder.create());
            } catch (RuntimeException e) {
                LOG.log(Level.SEVERE, "unable to process type " + pat, e);
            }
        }
    }
}