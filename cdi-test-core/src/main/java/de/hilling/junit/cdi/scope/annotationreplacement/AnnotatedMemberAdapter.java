package de.hilling.junit.cdi.scope.annotationreplacement;

import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotatedMemberAdapter<T> implements AnnotatedMember<T> {
    private final AnnotatedMember<? super T>             member;
    private Map<Class<? extends Annotation>, Annotation> replacementMap;

    AnnotatedMemberAdapter(AnnotatedMember<? super T> member, Map<Class<? extends Annotation>, Annotation> replacementMap) {
        this.member = member;
        this.replacementMap = replacementMap;
    }

    @Override
    public boolean isStatic() {
        return member.isStatic();
    }

    @SuppressWarnings("unchecked")
    @Override
    public AnnotatedType<T> getDeclaringType() {
        return (AnnotatedType<T>) member.getDeclaringType();
    }

    @Override
    public Type getBaseType() {
        return member.getBaseType();
    }

    @Override
    public Set<Type> getTypeClosure() {
        return member.getTypeClosure();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        if (member.isAnnotationPresent(annotationType)) {
            return member.getAnnotation(annotationType);
        } else {
            return (A) replacementMap.get(annotationType);
        }
    }

    @Override
    public Set<Annotation> getAnnotations() {
        Set<Annotation> annotations = new HashSet<>();

        Set<Annotation> superAnnotations = member.getAnnotations();
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
    public Member getJavaMember() {
        return member.getJavaMember();
    }

}