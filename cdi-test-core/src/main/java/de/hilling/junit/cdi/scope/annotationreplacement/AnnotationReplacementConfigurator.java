package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Interface for configuration replacement
 */
public interface AnnotationReplacementConfigurator {

    /**
     * Liefert eine Map für die Ersetzung von Annotationen.
     * 
     * @return {@link java.util.Map}
     */
    Map<Class<? extends Annotation>, Annotation> getReplacementMap();
}
