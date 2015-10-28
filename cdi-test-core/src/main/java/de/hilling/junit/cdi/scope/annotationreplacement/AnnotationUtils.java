package de.hilling.junit.cdi.scope.annotationreplacement;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities for annotation replacement.
 */
public final class AnnotationUtils {
    private static final Logger LOG = Logger.getLogger(AnnotationUtils.class.getCanonicalName());

    private AnnotationUtils() {
    }

    static Annotation replaceAnnotationFrom(Annotation annotation,
                                            Map<Class<? extends Annotation>, Annotation> replacementMap) {
        final Class<? extends Annotation> annotationClass = annotation.annotationType();
        if (replacementMap.containsKey(annotationClass)) {
            return replacementMap.get(annotationClass);
        } else {
            return annotation;
        }
    }

    static boolean isAnnotationPresentOn(Class<? extends Annotation> annotationType,
                                         Set<Annotation> currentAnnotations) {
        List<Class<? extends Annotation>> currentClasses = new ArrayList<>();
        for (Annotation annotation : currentAnnotations) {
            currentClasses.add(annotation.getClass());
        }
        return currentClasses.contains(annotationType);
    }

    /**
     * Add an annotation to the class represented by pat.
     * @param pat ProcessAnnotatedType for bean.
     * @param annotation annotation literal to add to the type.
     * @param <X> any type.
     */
    public static <X> void addClassAnnotation(ProcessAnnotatedType<X> pat, AnnotationLiteral<?> annotation) {
        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<>();
        builder.readFromType(pat.getAnnotatedType());
        builder.addToClass(annotation);
        try {
            pat.setAnnotatedType(builder.create());
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "unable to process type " + pat, e);
        }
    }
}
