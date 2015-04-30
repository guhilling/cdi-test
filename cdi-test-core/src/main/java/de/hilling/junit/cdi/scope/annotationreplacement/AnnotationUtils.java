package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utilities for annotation replacement.
 */
public final class AnnotationUtils {

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

    public static boolean isAnnotationPresentOn(Class<? extends Annotation> annotationType,
                                                Set<Annotation> currentAnnotations) {
        List<Class<? extends Annotation>> currentClasses = new ArrayList<>();
        for (Annotation annotation : currentAnnotations) {
            currentClasses.add(annotation.getClass());
        }
        return currentClasses.contains(annotationType);
    }
    
}
