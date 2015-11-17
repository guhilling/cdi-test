package de.hilling.junit.cdi;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Vetoed;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

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
