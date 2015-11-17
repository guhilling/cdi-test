package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Alternative
public class AnnotationReplacementAdapter<T>
extends AnnotatedTypeAdapter<T> {
    private final Map<Class<? extends Annotation>, Annotation> replacementMap;

    public AnnotationReplacementAdapter(AnnotatedType<T> delegate) {
        super(delegate);
        this.replacementMap = AnnotationReplacementHolder.getInstance().getReplacementMap();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        if (super.isAnnotationPresent(annotationType)) {
            return super.getAnnotation(annotationType);
        } else {
            return (A) replacementMap.get(annotationType);
        }
    }

    @Override
    public Set<Annotation> getAnnotations() {
        Set<Annotation> annotations = new HashSet<>();

        Set<Annotation> superAnnotations = super.getAnnotations();
        for (Annotation annotation : superAnnotations) {
            annotations.add(AnnotationUtils.replaceAnnotationFrom(annotation, replacementMap));
        }
        return annotations;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return AnnotationUtils.isAnnotationPresentOn(annotationType, getAnnotations());
    }

    @Override
    public Set<AnnotatedMethod<? super T>> getMethods() {
        Set<AnnotatedMethod<? super T>> result = new HashSet<>();
        for (final AnnotatedMethod<? super T> field : super.getMethods()) {
            result.add(new AnnotatedMethodAdapter<>(field, replacementMap));
        }
        return result;
    }

    @Override
    public Set<AnnotatedField<? super T>> getFields() {
        Set<AnnotatedField<? super T>> result = new HashSet<>();
        for (final AnnotatedField<? super T> field : super.getFields()) {
            result.add(new AnnotatedFieldAdapter<>(field, replacementMap));
        }
        return result;
    }
}