package de.hilling.junit.cdi.testing;

import static org.mockito.Mockito.verify;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class InjectionTest extends BaseTest {

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private ApplicationBeanWithResource applicationBeanWithResource;

    @Test
    public void verifyInjections() {
        Assert.assertNotNull(applicationBean.getOtherApplicationBean());
        Assert.assertNotNull(applicationBeanWithResource.getOtherApplicationBean());
    }

}
