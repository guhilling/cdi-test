package de.hilling.junit.cdi.scope;

import jakarta.enterprise.inject.Typed;
import jakarta.enterprise.inject.spi.AnnotatedType;
import jakarta.enterprise.inject.spi.ProcessAnnotatedType;
import jakarta.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import jakarta.enterprise.util.AnnotationLiteral;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.ImmutableActivatableTestImplementation;

/**
 * Prepare activatable alternatives.
 *
 * @param <X> Class to be considered.
 */
class ActivatableAlternativeBuilder<X> {
    private final ProcessAnnotatedType<X> pat;
    private final AnnotatedType<X>        type;
    private final Class<X>                javaClass;

    ActivatableAlternativeBuilder(ProcessAnnotatedType<X> pat) {
        this.pat = pat;
        type = pat.getAnnotatedType();
        javaClass = type.getJavaClass();
    }

    ActivatableTestImplementation invoke() {
        ActivatableTestImplementation implementation = type.getAnnotation(ActivatableTestImplementation.class);
        AnnotatedTypeConfigurator<X> configureAnnotatedType = pat.configureAnnotatedType();
        if (implementation.value().length == 0) {
            configureAnnotatedType.remove(a -> a.annotationType().equals(ActivatableTestImplementation.class));
            implementation = ImmutableActivatableTestImplementation.builder()
                    .value(determineUniqueSuperclass())
                    .build();
            configureAnnotatedType.add(implementation);
        }
        configureAnnotatedType.add(new TypedLiteral() {
            @Override
            public Class<?>[] value() {
                return new Class[]{javaClass};
            }
        });
        return implementation;
    }

    private Class<?> determineUniqueSuperclass() {
        Class<? super X> superclass = javaClass.getSuperclass();
        if (superclass != null) {
            return superclass;
        } else {
            throw new CdiTestException("No unique interface or superclass found on '" + javaClass + "'. You have to provide value() in ActivatableTestImplementation in this case!");
        }
    }

    private abstract static class TypedLiteral extends AnnotationLiteral<Typed> implements Typed {
    }

}
