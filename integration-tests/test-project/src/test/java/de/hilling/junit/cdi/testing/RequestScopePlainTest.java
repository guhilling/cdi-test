package de.hilling.junit.cdi.testing;

import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class RequestScopePlainTest extends BaseTest {

    private static final String SAMPLE = "sample";

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private RequestBean requestBean;

    @Test
    public void setAttributeTransitive() {
        Assert.assertNull(applicationBean.getAttribute());
        requestBean.setAttribute(SAMPLE);
        assertEquals(SAMPLE, applicationBean.getAttribute());
    }

}
