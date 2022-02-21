package de.hilling.junit.cdi.junit;

import net.bytebuddy.dynamic.DynamicType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class CompanionClassResolver {

    private static final Map<String, DynamicType.Unloaded<Object>> UNLOADED_COMPANION_CLASSES = new ConcurrentHashMap<>();
    private static final Map<String, Class<?>>                     COMPANION_CLASSES          = new ConcurrentHashMap<>();

    private CompanionClassResolver() {
    }

    static void putCompanionClass(String testClassName, DynamicType.Unloaded<Object> companionClass) {
        if (UNLOADED_COMPANION_CLASSES.put(testClassName, companionClass) != null) {
            throw new IllegalStateException("element already set: " + testClassName);
        }
    }

    public static Class<?> getCompanionClass(String testClassName) {
        return COMPANION_CLASSES.get(testClassName);
    }

    public static void loadClasses(ClassLoader classLoader) {
        UNLOADED_COMPANION_CLASSES.forEach((name, unloaded) -> {
            Class<?> clazz = unloaded.load(classLoader).getLoaded();
            COMPANION_CLASSES.put(name, clazz);
        });
    }

    public static Collection<Class<?>> getAllCompanionClasses() {
        return COMPANION_CLASSES.values();
    }
}