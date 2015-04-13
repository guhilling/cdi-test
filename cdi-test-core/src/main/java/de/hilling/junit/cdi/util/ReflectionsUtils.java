package de.hilling.junit.cdi.util;

import de.hilling.junit.cdi.CdiUnitRunner;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionsUtils {

    private static final String[] SYSTEM_PACKAGES = {
            "java",
            "javax",
            "com.sun",
            "org.apache.deltaspike",
            "org.jboss.weld"
    };

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        if (!superClass.equals(Object.class)) {
            result.addAll(getAllFields(superClass));
        }
        return result;
    }

    public static Class<? extends Object> getOriginalClass(
            Class<? extends Object> clazz) {
        String canonicalName = clazz.getCanonicalName();
        if (canonicalName.contains("$")) {
            try {
                if (clazz.getPackage() == null)
                    return Class.forName(canonicalName.substring(canonicalName.lastIndexOf(".") + 1,
                                                                 canonicalName.indexOf("$")));
                return Class.forName(canonicalName.substring(0,
                        canonicalName.indexOf("$")));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("unable to find original class", e);
            }
        } else {
            return clazz;
        }
    }

    /**
     * Determine is the given class is a junit cdi test.
     * <p>
     *     This is done by checking for the annotation {@link RunWith} annotation.
     * </p>
     * @param javaClass
     * @param <X>
     * @return true if it is a junit test.
     */
    public static <X> boolean isTestClass(Class<X> javaClass) {
        if (javaClass.isAnnotationPresent(RunWith.class)) {
            RunWith annotation = javaClass.getAnnotation(RunWith.class);
            return annotation.value().isAssignableFrom(CdiUnitRunner.class);
        } else {
            return false;
        }
    }

    /**
     * Determine if a proxy should be created for the given class.
     * <p>
     *     The following classes will be excluded:
     *     <ul>
     *         <li>System classes, including those from weld and deltaspike.</li>
     *         <li>Classes that are not proxyable.</li>
     *     </ul>
     * </p>
     *
     * @param javaClass
     * @param <X>
     * @return true if a cdi proxy should be created.
     */
    public static <X> boolean shouldProxyCdiType(Class<X> javaClass) {
        return !isSystemClass(javaClass) && isProxyable(javaClass);
    }

    private static <X> boolean isSystemClass(Class<X> javaClass) {
        if (javaClass.getPackage() == null)
            return false;

        String packageName = javaClass.getPackage().getName();
        for(String packagePrefix: SYSTEM_PACKAGES) {
            if(packageName.startsWith(packagePrefix)) {
                return true;
            }
        }
        return false;
    }

    private static <X> boolean isProxyable(Class<X> javaClass) {
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

    private static <X> boolean hasFinalMethods(Class<X> javaClass) {
        Method[] methods = javaClass.getMethods();
        for (Method method : methods) {
            if (method.getDeclaringClass().getPackage() != null &&
                method.getDeclaringClass().getPackage()
                      .getName().startsWith("java.lang")) {
                continue;
            }
            if (Modifier.isFinal(method.getModifiers())) {
                return true;
            }
        }
        return false;
    }

    private static <X> boolean hasPublicConstructor(Class<X> javaClass) {
        try {
            if (!Modifier.isPublic(javaClass.getConstructor().getModifiers())) {
                return false;
            }
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }
}
