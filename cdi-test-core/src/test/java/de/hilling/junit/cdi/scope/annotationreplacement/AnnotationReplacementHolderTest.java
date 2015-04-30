package de.hilling.junit.cdi.scope.annotationreplacement;

import org.junit.Test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import java.lang.annotation.Annotation;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Map.Entry<Class<? extends Annotation>, Annotation> annotationEntry = replacementMap.entrySet().iterator().next();
        assertEquals(SessionScoped.class, annotationEntry.getKey());
        assertTrue(annotationEntry.getValue() instanceof ApplicationScoped);
    }

    private void createHolder(String resourceName) {
        holder = new AnnotationReplacementHolder(resourceName);
    }
}