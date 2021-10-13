package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import de.hilling.junit.cdi.util.ReflectionsUtils;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Alternative
public class AnnotationReplacementBuilder<T> {
    private final Map<Class<? extends Annotation>, Annotation> replacementMap;
    private final AnnotatedType<T>                             delegate;
    private final AnnotatedTypeConfigurator<T> configurator;

    public AnnotationReplacementBuilder(ProcessAnnotatedType<T> pat) {
        this.delegate = pat.getAnnotatedType();
        this.configurator = pat.configureAnnotatedType();
        this.replacementMap = AnnotationReplacementHolder.getInstance().getReplacementMap();
    }

    public AnnotatedType<T> invoke() {
        if (ReflectionsUtils.isPossibleCdiBean(delegate.getJavaClass())) {
            final AnnotatedTypeBuilder<T> typeBuilder = new AnnotatedTypeBuilder<>();
            typeBuilder.readFromType(delegate);
            addAnnotations(typeBuilder);
            return typeBuilder.create();
        } else {
            return delegate;
        }
    }

    private void addAnnotations(AnnotatedTypeBuilder<T> typeBuilder) {
        for (Map.Entry<Class<? extends Annotation>, Annotation> replacement : replacementMap.entrySet()) {
            final Class<? extends Annotation> toReplace = replacement.getKey();
            Annotation replacementValue = replacement.getValue();
            if (delegate.isAnnotationPresent(toReplace)) {
                typeBuilder.addToClass(replacementValue);
            }
            delegate.getFields().stream()
                    .filter(f -> f.isAnnotationPresent(toReplace))
                    .forEach(f -> typeBuilder.addToField(f, replacementValue));
            delegate.getMethods().stream()
                    .filter(m -> m.isAnnotationPresent(toReplace))
                    .forEach(m -> typeBuilder.addToMethod(m, replacementValue));
            delegate.getConstructors().stream()
                    .filter(c -> c.isAnnotationPresent(toReplace))
                    .forEach(c -> typeBuilder.addToConstructor(c, replacementValue));
        }
    }
}