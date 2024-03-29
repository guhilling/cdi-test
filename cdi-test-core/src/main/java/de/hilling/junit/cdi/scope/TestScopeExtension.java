package de.hilling.junit.cdi.scope;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.*;

import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.annotationreplacement.AnnotationReplacementBuilder;
import de.hilling.junit.cdi.scope.context.TestContext;
import de.hilling.junit.cdi.scope.context.TestSuiteContext;
import de.hilling.junit.cdi.util.ReflectionsUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;

/**
 * CDI {@link Extension} to enable proxying of (nearly) all method invocations. <p> By
 * default, these are all classes, except: <ul> <li>Anonymous classes.</li> <li>Enums.</li> </ul> To preventing
 * <em>everything</em> from being proxied it is possible to define explicit packages.
 */
@BypassTestInterceptor
public class TestScopeExtension implements Extension, Serializable {

    private static final    long                         serialVersionUID = 1L;
    private static final    Logger                         LOG            = Logger.getLogger(
    TestScopeExtension.class.getCanonicalName());
    private final transient Map<Class<?>, ActivatableTestImplementation> decoratedTypes = new HashMap<>();

    /**
     * Add contexts after bean discovery.
     *
     * @param afterBeanDiscovery AfterBeanDiscovery
     */
    public void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery.addContext(new TestSuiteContext());
        afterBeanDiscovery.addContext(new TestContext());
    }

    ActivatableTestImplementation annotationsFor(Class<?> clazz) {
        return decoratedTypes.get(clazz);
    }

    /**
     * Use {@link AfterTypeDiscovery} to add Stereotype.
     *
     * @param afterTypeDiscovery type meta information.
     */
    public void afterTypeDiscovery(@Observes AfterTypeDiscovery afterTypeDiscovery) {
        afterTypeDiscovery.getAlternatives().add(GlobalTestImplementation.class);
    }

    public <X> void processAnnotatedTypes(@Observes ProcessAnnotatedType<X> pat) {
        LOG.log(FINE, "processing type {0}", pat);
        new AnnotationReplacementBuilder<>(pat).invoke();
        AnnotatedType<X> type = pat.getAnnotatedType();
        final Class<X> javaClass = type.getJavaClass();
        if (javaClass.isAnnotationPresent(ActivatableTestImplementation.class)) {
            decoratedTypes.put(pat.getAnnotatedType().getJavaClass(), new ActivatableAlternativeBuilder<>(pat).invoke());
        } else if (ReflectionsUtils.shouldProxyCdiType(javaClass)) {
            pat.configureAnnotatedType().add(ImmutableReplaceable.builder().build());
        }
    }
}