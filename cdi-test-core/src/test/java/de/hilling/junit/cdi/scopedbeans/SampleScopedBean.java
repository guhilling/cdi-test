package de.hilling.junit.cdi.scopedbeans;

import java.io.Serializable;

import de.hilling.junit.cdi.SampleAnnotation;

@SampleAnnotation
public class SampleScopedBean extends ScopedBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fieldOne;

    public String getFieldOne() {
        return fieldOne;
    }
}