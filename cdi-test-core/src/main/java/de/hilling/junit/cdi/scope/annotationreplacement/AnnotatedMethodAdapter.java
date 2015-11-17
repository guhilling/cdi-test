package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;

class AnnotatedMethodAdapter<T>
extends AnnotatedMemberAdapter<T> implements AnnotatedMethod<T> {
    private AnnotatedMethod<T> delegate;

    @SuppressWarnings("unchecked")
    AnnotatedMethodAdapter(AnnotatedMethod<? super T> delegate,
                           Map<Class<? extends Annotation>, Annotation> replacementMap) {
        super(delegate, replacementMap);
        this.delegate = (AnnotatedMethod<T>) delegate;
    }

    @Override
    public List<AnnotatedParameter<T>> getParameters() {
        return delegate.getParameters();
    }

    @Override
    public Method getJavaMember() {
        return delegate.getJavaMember();
    }

}
