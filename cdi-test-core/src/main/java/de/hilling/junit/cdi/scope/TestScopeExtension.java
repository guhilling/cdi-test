package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.annotationreplacement.AnnotationReplacementBuilder;
import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.scope.context.TestSuiteContext;
import de.hilling.junit.cdi.util.ReflectionsUtils;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;

/**
 * CDI {@link javax.enterprise.inject.spi.Extension} to enable proxying of (nearly) all method invocations. <p> By
 * default, these are all classes, except: <ul> <li>Anonymous classes.</li> <li>Enums.</li> </ul> To preventing
 * <em>everything</em> from being proxied it is possible to define explicit packages.
 */
@BypassTestInterceptor
public class TestScopeExtension implements Extension, Serializable {

    private static final    long                         serialVersionUID = 1L;
    private static final    Logger                       LOG              = Logger.getLogger(
    TestScopeExtension.class.getCanonicalName());
    private final transient Map<Class<?>, AnnotatedType<?>> decoratedTypes   = new HashMap<>();

    /**
     * Add contexts after bean discovery.
     *
     * @param afterBeanDiscovery AfterBeanDiscovery
     */
    public void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery.addContext(new TestSuiteContext());
        afterBeanDiscovery.addContext(new TestContext());
    }

    AnnotatedType<?> decoratedTypeFor(Class<?> clazz) {
        return decoratedTypes.get(clazz);
    }

    /**
     * Use {@link javax.enterprise.inject.spi.AfterTypeDiscovery} to add Stereotype.
     *
     * @param afterTypeDiscovery type meta information.
     */
    public void afterTypeDiscovery(@Observes AfterTypeDiscovery afterTypeDiscovery) {
        afterTypeDiscovery.getAlternatives().add(GlobalTestImplementation.class);
    }

    public <T> void replaceAnnotations(@Observes ProcessAnnotatedType<T> pat) {
        LOG.log(FINE, "processing type {0}", pat);
        pat.setAnnotatedType(new AnnotationReplacementBuilder<>(pat).invoke());
        updateDecoratedTypes(pat);
    }

    private <T> void updateDecoratedTypes(ProcessAnnotatedType<T> pat) {
        decoratedTypes.put(pat.getAnnotatedType().getJavaClass(), pat.getAnnotatedType());
    }

    public <X> void processAnnotatedTypes(@Observes ProcessAnnotatedType<X> pat) {
        AnnotatedType<X> type = pat.getAnnotatedType();
        final Class<X> javaClass = type.getJavaClass();
        if (javaClass.isAnnotationPresent(ActivatableTestImplementation.class)) {
            new ActivatableAlternativeBuilder<>(pat).invoke();
        } else if (ReflectionsUtils.shouldProxyCdiType(javaClass)) {
            pat.configureAnnotatedType().add(ImmutableReplaceable.builder().build());
        }
        updateDecoratedTypes(pat);
    }
}