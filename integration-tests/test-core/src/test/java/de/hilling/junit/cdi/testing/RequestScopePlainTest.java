package de.hilling.junit.cdi.testing;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RequestScopePlainTest extends BaseTest {

    private static final String SAMPLE = "sample";

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private RequestBean requestBean;

    @Test
    void setAttributeTransitive() {
        Assertions.assertNull(applicationBean.getAttribute());
        requestBean.setAttribute(SAMPLE);
        assertEquals(SAMPLE, applicationBean.getAttribute());
    }

}
