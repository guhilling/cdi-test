package de.hilling.junit.cdi.jee.jta;

import jakarta.enterprise.inject.Vetoed;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

import org.jboss.weld.resources.spi.ClassFileInfo;
import org.jboss.weld.resources.spi.ClassFileServices;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.scope.TestScoped;

@TestScoped
public class ClassloadService implements ClassFileServices {
    @Override
    public ClassFileInfo getClassFileInfo(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return new ClassFileInfo() {
                @Override
                public String getClassName() {
                    return className;
                }

                @Override
                public String getSuperclassName() {
                    return clazz.getSuperclass().getName();
                }

                @Override
                public boolean isAnnotationDeclared(Class<? extends Annotation> annotationType) {
                    return clazz.isAnnotationPresent(annotationType);
                }

                @Override
                public boolean containsAnnotation(Class<? extends Annotation> annotationType) {
                    return clazz.isAnnotationPresent(annotationType);
                }

                @Override
                public int getModifiers() {
                    return Modifier.PUBLIC;
                }

                @Override
                public boolean hasCdiConstructor() {
                    return true;
                }

                @Override
                public boolean isAssignableFrom(Class<?> javaClass) {
                    return clazz.isAssignableFrom(javaClass);
                }

                @Override
                public boolean isAssignableTo(Class<?> javaClass) {
                    return javaClass.isAssignableFrom(clazz);
                }

                @Override
                public boolean isVetoed() {
                    return clazz.isAnnotationPresent(Vetoed.class);
                }

                @Override
                public NestingType getNestingType() {
                    return NestingType.TOP_LEVEL;
                }
            };
        } catch (ClassNotFoundException e) {
            throw new CdiTestException("unable to load class", e);
        }
    }

    @Override
    public void cleanupAfterBoot() {
    }

    @Override
    public void cleanup() {
    }
}
