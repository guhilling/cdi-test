package de.hilling.junit.cdi.scope.annotationreplacement;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import de.hilling.junit.cdi.util.ReflectionsUtils;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Alternative
public class AnnotationReplacementBuilder<T> {
    private final Map<Class<? extends Annotation>, Annotation> replacementMap;
    private final AnnotatedType<T>                             delegate;

    public AnnotationReplacementBuilder(AnnotatedType<T> delegate) {
        this.delegate = delegate;
        this.replacementMap = AnnotationReplacementHolder.getInstance().getReplacementMap();
    }

    public AnnotatedType<T> invoke() {
        if (ReflectionsUtils.isPossibleCdiBean(delegate.getJavaClass())) {
            final AnnotatedTypeBuilder<T> typeBuilder = new AnnotatedTypeBuilder<>();
            typeBuilder.readFromType(delegate);
            for (Map.Entry<Class<? extends Annotation>, Annotation> replacement : replacementMap.entrySet()) {
                final Class<? extends Annotation> toReplace = replacement.getKey();
                Annotation replacementValue = replacement.getValue();
                if (delegate.isAnnotationPresent(toReplace)) {
                    typeBuilder.addToClass(replacementValue);
                }
                for (AnnotatedField<? super T> field : delegate.getFields()) {
                    if(field.isAnnotationPresent(toReplace)) {
                        typeBuilder.addToField(field, replacementValue);
                    }
                }
                for (AnnotatedMethod<? super T> method : delegate.getMethods()) {
                    if(method.isAnnotationPresent(toReplace)) {
                        typeBuilder.addToMethod(method, replacementValue);
                    }
                }
                for (AnnotatedConstructor<T> constructor : delegate.getConstructors()) {
                    if(constructor.isAnnotationPresent(toReplace)) {
                        typeBuilder.addToConstructor(constructor, replacementValue);
                    }
                }
            }
            return typeBuilder.create();
        } else {
            return delegate;
        }
    }
}