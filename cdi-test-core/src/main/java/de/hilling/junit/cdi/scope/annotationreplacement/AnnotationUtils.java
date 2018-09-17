package de.hilling.junit.cdi.scope.annotationreplacement;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.inject.spi.ProcessAnnotatedType;
import java.lang.annotation.Annotation;

/**
 * Utilities for annotation replacement.
 */
public final class AnnotationUtils {

    private AnnotationUtils() {
    }

    /**
     * Add an annotation to the class represented by pat.
     *
     * @param pat        ProcessAnnotatedType for bean.
     * @param annotation annotation literal to add to the type.
     * @param <X>        any type.
     */
    public static <X> void addClassAnnotation(ProcessAnnotatedType<X> pat, Annotation annotation) {
        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<>();
        builder.readFromType(pat.getAnnotatedType());
        pat.setAnnotatedType(builder.addToClass(annotation).create());
    }
}
