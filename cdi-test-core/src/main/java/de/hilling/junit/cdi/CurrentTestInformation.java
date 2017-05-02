package de.hilling.junit.cdi;

import java.lang.reflect.Method;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@BypassTestInterceptor
@TestSuiteScoped
public class CurrentTestInformation {

   private  Class<?> testClass;
   private  Method method;

   public Class<?> getTestClass() {
      return testClass;
   }

   public void setTestClass(Class<?> testClass) {
      this.testClass = testClass;
   }

   public Method getMethod() {
      return method;
   }

   public void setMethod(Method method) {
      this.method = method;
   }
}
