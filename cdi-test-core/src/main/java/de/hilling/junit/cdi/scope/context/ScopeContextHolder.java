package de.hilling.junit.cdi.scope.context;

import java.util.Map;

/**
 * author: fseemann on 29.04.2015.
 */
public interface ScopeContextHolder {
    <T> void putBean(CustomScopeInstance<?> customInstance);
    Map<Class<?>, CustomScopeInstance<?>> getBeans();
    <T> CustomScopeInstance<T> getBean(Class<T> type);
    void clear();
}
