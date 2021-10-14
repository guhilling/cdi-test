package de.hilling.junit.cdi.scope.annotationreplacement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;

import org.junit.jupiter.api.Test;

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

    private void createHolder(String resourceName) {
        holder = new AnnotationReplacementHolder(resourceName);
    }
}