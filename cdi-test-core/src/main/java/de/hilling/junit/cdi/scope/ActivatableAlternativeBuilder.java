package de.hilling.junit.cdi.scope;

import javax.enterprise.inject.Typed;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;

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

    public ActivatableAlternativeBuilder(ProcessAnnotatedType<X> pat) {
        this.pat = pat;
        type = pat.getAnnotatedType();
        javaClass = type.getJavaClass();
        builder = new AnnotatedTypeBuilder<>();
        builder.readFromType(type);
    }

    public void invoke() {
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
        builder.addToClass(new ActivatableTestImplementationLiteral() {
            @Override
            public Class<?>[] value() {
                return new Class<?>[]{determineUniqueSuperclass()};
            }
        });
    }

    private Class<?> determineUniqueSuperclass() {
        Class<? super X> superclass = javaClass.getSuperclass();
        if (superclass != null) {
            return superclass;
        } else {
            throw new CdiTestException("No unique interface or superclass found on '" + javaClass + "'. You have to provide value() in ActivatableTestImplementation in this case!");
        }
    }

    private static abstract class TypedLiteral extends AnnotationLiteral<Typed> implements Typed {
    }

    private static abstract class ActivatableTestImplementationLiteral extends AnnotationLiteral<ActivatableTestImplementation> implements ActivatableTestImplementation {
    }


}
