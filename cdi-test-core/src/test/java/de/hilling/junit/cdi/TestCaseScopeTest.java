package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.scopedbeans.ApplicationScopedBean;
import de.hilling.junit.cdi.scopedbeans.DependentScopedBean;
import de.hilling.junit.cdi.scopedbeans.RequestScopedBean;
import de.hilling.junit.cdi.scopedbeans.SampleScopedBean;
import de.hilling.junit.cdi.scopedbeans.ScopedBean;
import de.hilling.junit.cdi.scopedbeans.SessionScopedBean;
import de.hilling.junit.cdi.scopedbeans.TestScopedBean;
import de.hilling.junit.cdi.scopedbeans.TestSuiteScopedBean;

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
    @Inject
    private DependentScopedBean dependentScopedBean;

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
        assertNotSame(dependent1, dependent2);
    }

    @Test
    public void testSampleReplacedToDependent() {

    }

    private void assertInstances() {
        assertInstanceNotSame(testScopedBean);
        assertInstanceNotSame(applicationScopedBean);
        assertInstanceNotSame(requestScopedBean);
        assertInstanceNotSame(sessionScopedBean);
        assertInstanceNotSame(sampleScopedBean);
        assertInstanceSame(testSuiteScopedBean);
        assertInstanceSame(dependentScopedBean);
    }

    private void assertInstanceNotSame(ScopedBean bean) {
        Class<? extends ScopedBean> beanKey = bean.getClass();
        if (firstUuids.containsKey(beanKey)) {
            assertNotSame(firstUuids.get(beanKey), bean.getUuid());
        } else {
            firstUuids.put(beanKey, bean.getUuid());
        }
    }

    private void assertInstanceSame(ScopedBean bean) {
        Class<? extends ScopedBean> beanKey = bean.getClass();
        if (firstUuids.containsKey(beanKey)) {
            assertSame(firstUuids.get(beanKey), bean.getUuid());
        } else {
            firstUuids.put(beanKey, bean.getUuid());
        }
    }
}
