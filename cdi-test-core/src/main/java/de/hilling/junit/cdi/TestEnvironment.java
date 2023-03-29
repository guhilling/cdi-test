package de.hilling.junit.cdi;

import java.lang.reflect.Method;

import org.jboss.weld.proxy.WeldClientProxy;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

/**
 * Keep information about the currently running test available as cdi component.
 * <p>
 *     This bean must actually be {@link TestSuiteScoped} because it needs to be configured before
 *     the {@link de.hilling.junit.cdi.scope.TestScoped} context is activated.
 * </p>
 */
@BypassTestInterceptor
@TestSuiteScoped
public class TestEnvironment {

    private WeldClientProxy testInstance;
    private Method testMethod;
    private String testName;

    public void setTestInstance(WeldClientProxy testInstance) {
        this.testInstance = testInstance;
    }

    public Method getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(Method requiredTestMethod) {

        this.testMethod = requiredTestMethod;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public Object getTestInstance() {
        return testInstance;
    }

    public Object getTestTarget() {
        return testInstance.getMetadata().getContextualInstance();
    }

    public Class<?> getTestClass() {
        return testInstance.getMetadata().getContextualInstance().getClass();
    }
}
