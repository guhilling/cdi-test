package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;

/**
 * Implement {@link AnnotatedType}, delegating to existing instance by default.
 *
 * @param <X> Type of annotation being replaced.
 * @author gunnar
 */
abstract class AnnotatedTypeAdapter<X> implements AnnotatedType<X> {
    private final AnnotatedType<X> delegate;

    public AnnotatedTypeAdapter(AnnotatedType<X> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Type getBaseType() {
        return delegate.getBaseType();
    }

    @Override
    public Set<Type> getTypeClosure() {
        return delegate.getTypeClosure();
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return delegate.getAnnotation(annotationType);
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> Set<T> getAnnotations(Class<T> annotationType) {
        try {
            final Method getAnnotations = delegate.getClass().getMethod("getAnnotations", annotationType.getClass());
            return (Set<T>) getAnnotations.invoke(getAnnotations, annotationType);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("no such method, jdk mix?", e);
        }
    }

    @Override
    public Set<Annotation> getAnnotations() {
        return delegate.getAnnotations();
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return delegate.isAnnotationPresent(annotationType);
    }

    @Override
    public Class<X> getJavaClass() {
        return delegate.getJavaClass();
    }

    @Override
    public Set<AnnotatedConstructor<X>> getConstructors() {
        return delegate.getConstructors();
    }

    @Override
    public Set<AnnotatedMethod<? super X>> getMethods() {
        return delegate.getMethods();
    }

    @Override
    public Set<AnnotatedField<? super X>> getFields() {
        return delegate.getFields();
    }

}
