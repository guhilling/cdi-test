package de.hilling.junit.cdi.scope.annotationreplacement;

import org.junit.Before;

import javax.enterprise.inject.spi.AnnotatedMember;

public class AnnotatedMemberAdapterTest {

    private AnnotatedFieldAdapter<AnnotationTestCase> fieldAdapter;
    private AnnotatedMethodAdapter<AnnotationTestCase> methodAdapter;
    private AnnotationReplacementHolder replacementHolder;
    private AnnotatedMember<? super AnnotationTestCase> member;

    @Before
    public void setUp() {
        replacementHolder = AnnotationReplacementHolder.getInstance();
        fieldAdapter = new AnnotatedFieldAdapter<>(member, replacementHolder.getReplacementMap());
    }
}