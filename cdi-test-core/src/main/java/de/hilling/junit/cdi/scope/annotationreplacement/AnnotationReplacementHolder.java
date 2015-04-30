package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Load annotation replacements and provide them to the replacer.
 */
public class AnnotationReplacementHolder {

    private static final AnnotationReplacementHolder INSTANCE;
    private Map<Class<? extends Annotation>, Annotation> replacementMap = new HashMap<>();

    public Map<Class<? extends Annotation>, Annotation> getReplacementMap() {
        return replacementMap;
    }

    static {
        INSTANCE = new AnnotationReplacementHolder();
    }

    public static AnnotationReplacementHolder getInstance() {
        return INSTANCE;
    }
}
