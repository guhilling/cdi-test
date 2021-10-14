package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

import de.hilling.junit.cdi.util.ReflectionsUtils;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Alternative
public class AnnotationReplacementBuilder<T> {
    private final Map<Class<? extends Annotation>, Annotation> replacementMap;
    private final AnnotatedTypeConfigurator<T> configurator;

    public AnnotationReplacementBuilder(ProcessAnnotatedType<T> pat) {
        this.configurator = pat.configureAnnotatedType();
        this.replacementMap = AnnotationReplacementHolder.getInstance().getReplacementMap();
    }

    public void invoke() {
        if (ReflectionsUtils.isPossibleCdiBean(configurator.getAnnotated().getJavaClass())) {
            addAnnotations();
        }
    }

    private void addAnnotations() {
        for (Map.Entry<Class<? extends Annotation>, Annotation> replacement : replacementMap.entrySet()) {
            AnnotatedType<T> delegate = configurator.getAnnotated();
            final Class<? extends Annotation> toReplace = replacement.getKey();
            Annotation replacementValue = replacement.getValue();
            if (delegate.isAnnotationPresent(toReplace)) {
                configurator.add(replacementValue);
            }
            configurator.fields().stream()
                    .filter(f -> f.getAnnotated().isAnnotationPresent(toReplace))
                    .forEach(f -> f.add(replacementValue));
            configurator.methods().stream()
                    .filter(m -> m.getAnnotated().isAnnotationPresent(toReplace))
                    .forEach(m -> m.add(replacementValue));
            configurator.constructors().stream()
                    .filter(c -> c.getAnnotated().isAnnotationPresent(toReplace))
                    .forEach(c -> c.add(replacementValue));
        }
    }
}