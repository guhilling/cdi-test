package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.StrangeName$Object;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ReflectionsUtilsTest {

    @Test
    public void findIdenticalClass() {
        assertEquals(String.class,
                ReflectionsUtils.getOriginalClass(String.class));
    }

    @Test
    public void findOriginalClass() {
        ReflectionsUtilsTest utilsTestMock = Mockito
                .mock(ReflectionsUtilsTest.class);
        assertEquals(ReflectionsUtilsTest.class,
                ReflectionsUtils.getOriginalClass(utilsTestMock.getClass()));
    }


    @Test(expected = RuntimeException.class)
    public void failOnStrangeClassName() {
        ReflectionsUtils.getOriginalClass(StrangeName$Object.class);
    }

    @Test
    public void dontProxySystemClasses() {
        assertFalse(ReflectionsUtils.shouldProxyCdiType(Integer.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(CdiContainer.class));
    }

    @Test
    public void dontProxyNonProxyableClasses() {
        assertFalse(ReflectionsUtils.shouldProxyCdiType(FinalClass.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(ClassWithoutDefaultConstructor.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(ClassWithProtectedDefaultConstructor.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(ClassWithPrivateDefaultConstructor.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(SampleEnum.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(ClassWithFinalMethod.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(SubClassOfClassWithFinalMethod.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(new Object() {
        }.getClass()));
    }

    @Test
    public void proxyStandardClass() {
        assertTrue(ReflectionsUtils.shouldProxyCdiType(ReflectionsUtilsTest.class));
        try {
            assertTrue(ReflectionsUtils.shouldProxyCdiType(Class.forName("NoPackage")));
        } catch (ClassNotFoundException e) {
            assertFalse(true);
        }
    }

    @Test
    public void isTestClass() {
        assertTrue(ReflectionsUtils.isTestClass(CdiTestAbstract.class));
        assertTrue(ReflectionsUtils.isTestClass(InjectionTest.class));
        assertTrue(ReflectionsUtils.isTestClass(NotificationTest.class));
        assertFalse(ReflectionsUtils.isTestClass(ReflectionsUtilsTest.class));
    }

    public static final class FinalClass {
    }

    public static class ClassWithoutDefaultConstructor {
        public ClassWithoutDefaultConstructor(String name) {
        }
    }

    public static class SubClassOfClassWithFinalMethod extends ClassWithFinalMethod {
    }

    public static class ClassWithFinalMethod {
        public final void work() {
        }
    }

    public static class ClassWithProtectedDefaultConstructor {
        protected ClassWithProtectedDefaultConstructor() {
        }
    }

    public static class ClassWithPrivateDefaultConstructor {
        private ClassWithPrivateDefaultConstructor() {
        }
    }

    public enum SampleEnum {
        ONE,
        TWO
    }
}
