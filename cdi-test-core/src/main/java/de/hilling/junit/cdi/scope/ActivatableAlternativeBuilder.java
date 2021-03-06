package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.annotations.ImmutableActivatableTestImplementation;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.inject.Typed;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;

/**
 * Prepare activatable alternatives.
 *
 * @param <X>
 */
class ActivatableAlternativeBuilder<X> {
    private final ProcessAnnotatedType<X> pat;
    private final AnnotatedType<X> type;
    private final Class<X> javaClass;
    private final AnnotatedTypeBuilder<X> builder;

    ActivatableAlternativeBuilder(ProcessAnnotatedType<X> pat) {
        this.pat = pat;
        type = pat.getAnnotatedType();
        javaClass = type.getJavaClass();
        builder = new AnnotatedTypeBuilder<>();
        builder.readFromType(type);
    }

    void invoke() {
        ActivatableTestImplementation implementation = type.getAnnotation(ActivatableTestImplementation.class);
        if (implementation.value().length == 0) {
            guessReplacableTypes();
        }
        builder.addToClass(new TypedLiteral() {
            @Override
            public Class<?>[] value() {
                return new Class[]{javaClass};
            }
        });
        pat.setAnnotatedType(builder.create());
    }

    private void guessReplacableTypes() {
        builder.removeFromClass(ActivatableTestImplementation.class);
        final ImmutableActivatableTestImplementation.Builder anntationBuilder = ImmutableActivatableTestImplementation.builder();
        this.builder.addToClass(anntationBuilder.value(determineUniqueSuperclass()).build());
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
