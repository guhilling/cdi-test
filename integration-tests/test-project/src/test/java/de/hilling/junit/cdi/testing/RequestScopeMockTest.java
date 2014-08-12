package de.hilling.junit.cdi.testing;

import org.junit.Test;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

public class RequestScopeMockTest extends BaseTest {

    private static final String SAMPLE = "sample";

    @Mock
    private ApplicationBean applicationBean;

    @Inject
    private RequestBean requestBean;

    @Test
    public void setAttributeTransitive() {
        requestBean.setAttribute(SAMPLE);
        verify(applicationBean).setAttribute(SAMPLE);
    }

}
