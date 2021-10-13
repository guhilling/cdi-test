package de.hilling.junit.cdi.util;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utilities for reflection access.
 */
public final class ReflectionsUtils {

    private static final String[] SYSTEM_PACKAGES = {"java", "javax", "com.sun", "org.jboss", "org.wildfly", "jakarta"};

    private ReflectionsUtils() {
    }

    /**
     * Get all fields in a class.
     * @param clazz class.
     * @return list of fields.
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.equals(Object.class)) {
            result.addAll(getAllFields(superClass));
        }
        return result;
    }

    /**
     * Gets the proxied class in case of proxied classes (Mockito or cdi).
     * @param clazz class to find the "original" class for.
     * @return "original" class.
     */
    public static Class<?> getOriginalClass(Class<?> clazz) {
        String canonicalName = clazz.getCanonicalName();
        if (canonicalName.contains("$")) {
            try {
                return Class.forName(canonicalName.substring(0, canonicalName.indexOf('$')));
            } catch (ClassNotFoundException e) {
                throw new CdiTestException("unable to find original class", e);
            }
        } else {
            return clazz;
        }
    }

    /**
     * Determine if a proxy should be created for the given class.
     * <p>The following classes will be excluded:</p>
     * <ul>
     * <li>System classes, including those from weld.</li>
     * <li>Classes that are not proxyable.</li>
     * </ul>
     *
     * @param javaClass class to verify
     * @param <X>       type of class
     *
     * @return true if a cdi proxy should be created.
     */
    public static <X> boolean shouldProxyCdiType(Class<X> javaClass) {
        return !isSystemClass(javaClass) && isPossibleCdiBean(javaClass);
    }

    public static <X> boolean isSystemClass(Class<X> javaClass) {
        if (javaClass.isAnnotationPresent(BypassTestInterceptor.class)) {
            return true;
        }
        if (javaClass.getPackage() == null) {
            return false;
        }

        String packageName = javaClass.getPackage().getName();
        for (String packagePrefix : SYSTEM_PACKAGES) {
            if (packageName.startsWith(packagePrefix)) {
                return true;
            }
        }
        return false;
    }

    public static <X> boolean isPossibleCdiBean(Class<X> javaClass) {
        if (javaClass.isAnonymousClass()) {
            return false;
        }
        if (javaClass.isEnum()) {
            return false;
        }
        if (javaClass.isPrimitive()) {
            return false;
        }
        if (Modifier.isFinal(javaClass.getModifiers())) {
            return false;
        }
        if (!hasPublicConstructor(javaClass)) {
            return false;
        }
        if (hasFinalMethods(javaClass)) {
            return false;
        }
        return !javaClass.isEnum();
    }

    public static <X> boolean hasFinalMethods(Class<X> javaClass) {
        Method[] methods = javaClass.getMethods();
        for (Method method : methods) {
            if (method.getDeclaringClass().getPackage() != null && method.getDeclaringClass().getPackage().getName()
                                                                         .startsWith("java.lang")) {
                continue;
            }
            if (Modifier.isFinal(method.getModifiers())) {
                return true;
            }
        }
        return false;
    }

    public static <X> boolean hasPublicConstructor(Class<X> javaClass) {
        try {
            Constructor<X> constructor = javaClass.getConstructor();
            if (!Modifier.isPublic(constructor.getModifiers())) {
                return false;
            }
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    public static void setField(Object target, Object value, Field field) {
        field.setAccessible(true);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new CdiTestException("setting field failed", e);
        } finally {
            field.setAccessible(false);
        }
    }
}
