package de.hilling.junit.cdi.scope.annotationreplacement;

import java.util.logging.Logger;

import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

/**
 * Utilities for annotation replacement.
 */
public final class AnnotationUtils {
    private static final Logger LOG = Logger.getLogger(AnnotationUtils.class.getCanonicalName());

    private AnnotationUtils() {
    }

    /**
     * Add an annotation to the class represented by pat.
     *
     * @param pat        ProcessAnnotatedType for bean.
     * @param annotation annotation literal to add to the type.
     * @param <X>        any type.
     */
    public static <X> void addClassAnnotation(ProcessAnnotatedType<X> pat, AnnotationLiteral<?> annotation) {
        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<>();
        builder.readFromType(pat.getAnnotatedType());
        pat.setAnnotatedType(builder.addToClass(annotation).create());
    }
}
