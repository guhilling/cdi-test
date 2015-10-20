package de.hilling.junit.cdi.scope.annotationreplacement;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

@Alternative
public class AnnotationTestCase {

    @Inject
    private String name;
}
