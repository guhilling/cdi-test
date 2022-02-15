package de.hilling.junit.cdi;

import java.lang.reflect.Method;

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

    private Object testInstance;
    private Object cdiInstance;
    private Method testMethod;
    private String testName;

    public void setTestInstance(Object testInstance) {
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

    public Class<?> getTestClass() {
        return testInstance.getClass();
    }

    public Object getCdiInstance() {
        return cdiInstance;
    }

    public void setCdiInstance(Object cdiInstance) {
        this.cdiInstance = cdiInstance;
    }
}
