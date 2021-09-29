package de.hilling.junit.cdi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.hilling.junit.cdi.beans.StrangeName$Object;

class ReflectionsUtilsTest {

    @Test
    void findIdenticalClass() {
        assertEquals(String.class, ReflectionsUtils.getOriginalClass(String.class));
    }

    @Test
    void findOriginalClass() {
        ReflectionsUtilsTest utilsTestMock = Mockito.mock(ReflectionsUtilsTest.class);
        assertEquals(ReflectionsUtilsTest.class, ReflectionsUtils.getOriginalClass(utilsTestMock.getClass()));
    }

    @Test
    void failOnStrangeClassName() {
        assertThrows(RuntimeException.class, () -> ReflectionsUtils.getOriginalClass(StrangeName$Object.class));
    }

    @Test
    void dontProxySystemClasses() {
        assertFalse(ReflectionsUtils.shouldProxyCdiType(Integer.class));
        assertFalse(ReflectionsUtils.shouldProxyCdiType(CdiContainer.class));
    }

    @Test
    void dontProxyPrivateConstructor() {
        assertFalse(ReflectionsUtils.shouldProxyCdiType(ReflectionsUtils.class));
    }

    @Test
    void dontProxyProtectedConstructor() {
        assertFalse(ReflectionsUtils.hasPublicConstructor(TestClassNoPublicConstructor.class));
    }

    @Test
    void dontProxyPrimitives() {
        assertFalse(ReflectionsUtils.shouldProxyCdiType(Integer.TYPE));
    }

    @Test
    void dontProxyNonProxyableClasses() {
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

    public enum SampleEnum {
        ONE,
        TWO
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
}
