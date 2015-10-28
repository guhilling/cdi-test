package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.reflect.AnnotatedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Provide a mapping from an implementation to its annotated type.
 */
public final class AnnotatedTypesCache {

    private static final AnnotatedTypesCache INSTANCE = new AnnotatedTypesCache();

    private Map<Class<?>, AnnotatedType> annotatedTypes = new HashMap<>();

    private AnnotatedTypesCache() {
    }

    /**
     * Get the singletion instance of this cache.
     * @return
     */
    public static AnnotatedTypesCache getInstance() {
        return INSTANCE;
    }

    /**
     *
     * @param clazz
     * @param annotatedType
     */
    public void addAnnotatedType(Class<?> clazz, AnnotatedType annotatedType) {
        annotatedTypes.put(clazz, annotatedType);
    }
}
