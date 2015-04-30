package de.hilling.junit.cdi;

import de.hilling.junit.cdi.scopedbeans.*;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Test if only one test class.
 *
 * @author gunnar
 */
public class TestCaseScopeTest extends CdiTestAbstract {

    private static Map<Class<?>, UUID> firstUuids = new HashMap<>();

    @Inject
    private TestSuiteScopedBean testSuiteScopedBean;
    @Inject
    private TestScopedBean testScopedBean;
    @Inject
    private ApplicationScopedBean applicationScopedBean;
    @Inject
    private RequestScopedBean requestScopedBean;
    @Inject
    private SessionScopedBean sessionScopedBean;
    @Inject
    private SampleScopedBean sampleScopedBean;

    @Test
    public void testOne() {
        assertInstances();
    }

    @Test
    public void testTwo() {
        assertInstances();
    }

    @Test
    public void testDependents() {
        DependentScopedBean dependent1 = requestScopedBean.getDependentScopedBean();
        DependentScopedBean dependent2 = applicationScopedBean.getDependentScopedBean();
        Assert.assertNotSame(dependent1, dependent2);
    }

    private void assertInstances() {
        assertInstanceNotSame(testScopedBean);
        assertInstanceNotSame(applicationScopedBean);
        assertInstanceNotSame(requestScopedBean);
        assertInstanceNotSame(sessionScopedBean);
        assertInstanceSame(testSuiteScopedBean);
    }

    private void assertInstanceNotSame(ScopedBean bean) {
        Class<? extends ScopedBean> beanKey = bean.getClass();
        if (firstUuids.containsKey(beanKey)) {
            Assert.assertNotSame(firstUuids.get(beanKey), bean.getUuid());
        } else {
            firstUuids.put(beanKey, bean.getUuid());
        }
    }

    private void assertInstanceSame(ScopedBean bean) {
        Class<? extends ScopedBean> beanKey = bean.getClass();
        if (firstUuids.containsKey(beanKey)) {
            Assert.assertSame(firstUuids.get(beanKey), bean.getUuid());
        } else {
            firstUuids.put(beanKey, bean.getUuid());
        }
    }
}
