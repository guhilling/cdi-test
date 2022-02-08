package de.hilling.junit.cdi.junit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class CompanionClassResolver {

    private static final Map<String, Class> COMPANION_CLASSES = new ConcurrentHashMap<>();

    static void putCompanionClass(String testClassName, Class<?> companionClass) {
        if(COMPANION_CLASSES.put(testClassName, companionClass) != null) {
            throw new IllegalStateException("element already set: " + testClassName);
        }
    }
    
    
}
