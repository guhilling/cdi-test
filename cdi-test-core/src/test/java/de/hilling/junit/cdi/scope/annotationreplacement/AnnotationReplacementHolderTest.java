package de.hilling.junit.cdi.scope.annotationreplacement;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;

import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.service.OverriddenService;
import de.hilling.junit.cdi.service.OverriddenServiceImpl;

class AnnotationReplacementHolderTest {

    private AnnotationReplacementHolder holder;

    @Test
    void ignoreNoReplacementFound() {
        createHolder("no such resource");
        assertEquals(0, holder.getReplacementMap().size());
    }

    @Test
    void simpleReplacementWithComment() {
        createHolder("test-annotations.properties");
        Map<Class<? extends Annotation>, Annotation> replacementMap = holder.getReplacementMap();
        assertEquals(1, replacementMap.size());
        Map.Entry<Class<? extends Annotation>, Annotation> annotationEntry = replacementMap.entrySet().iterator()
                                                                                           .next();
        assertEquals(SessionScoped.class, annotationEntry.getKey());
        assertTrue(annotationEntry.getValue() instanceof ApplicationScoped);
    }

    @Test
    void noSuchClass() {
        assertThrows(RuntimeException.class, () -> createHolder("test-nosuchclass.properties"));
    }

    @Test
    void classNotAnAnnotation() {
        assertThrows(RuntimeException.class, () -> createHolder("test-noannotation.properties"));
    }

    @Test
    void invocationHandlerMethods() {
        Object proxyInstance = Proxy.newProxyInstance(getClass().getClassLoader(),
                                          new Class[]{OverriddenService.class},
                                          new AnnotationReplacementHolder.AnnotationInvocationHandler(OverriddenService.class));
        assertTrue(proxyInstance instanceof OverriddenService);
        OverriddenService serviceProxy = (OverriddenService) proxyInstance;
        assertNotEquals(serviceProxy, new OverriddenServiceImpl());
        assertEquals(0, serviceProxy.hashCode());
        assertEquals("de.hilling.junit.cdi.service.OverriddenService", serviceProxy.toString());
        assertNull(serviceProxy.serviceMethod());
        assertTrue(serviceProxy.equals(serviceProxy));
    }

    private void createHolder(String resourceName) {
        holder = new AnnotationReplacementHolder(resourceName);
    }
}