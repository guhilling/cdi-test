package de.hilling.junit.cdi.scope.annotationreplacement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;

import org.junit.jupiter.api.Test;

public class AnnotationReplacementHolderTest {

    private AnnotationReplacementHolder holder;

    @Test
    public void ignoreNoReplacementFound() {
        createHolder("no such resource");
        assertEquals(0, holder.getReplacementMap().size());
    }

    @Test
    public void simpleReplacementWithComment() {
        createHolder("test-annotations.properties");
        Map<Class<? extends Annotation>, Annotation> replacementMap = holder.getReplacementMap();
        assertEquals(1, replacementMap.size());
        Map.Entry<Class<? extends Annotation>, Annotation> annotationEntry = replacementMap.entrySet().iterator()
                                                                                           .next();
        assertEquals(SessionScoped.class, annotationEntry.getKey());
        assertTrue(annotationEntry.getValue() instanceof ApplicationScoped);
    }

    @Test
    public void noSuchClass() {
        assertThrows(RuntimeException.class, () -> createHolder("test-nosuchclass.properties"));
    }

    @Test
    public void classNotAnAnnotation() {
        assertThrows(RuntimeException.class, () -> createHolder("test-noannotation.properties"));
    }

    private void createHolder(String resourceName) {
        holder = new AnnotationReplacementHolder(resourceName);
    }
}