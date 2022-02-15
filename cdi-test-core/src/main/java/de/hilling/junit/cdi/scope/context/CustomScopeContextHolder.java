package de.hilling.junit.cdi.scope.context;

import jakarta.enterprise.inject.spi.Bean;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Store for beans in custom scopes.
 */
@BypassTestInterceptor
public class CustomScopeContextHolder implements ScopeContextHolder {
    private final Map<Class<?>, CustomScopeInstance<?>> beans;

    public CustomScopeContextHolder() {
        beans = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public Map<Class<?>, CustomScopeInstance<?>> getBeans() {
        return beans;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> CustomScopeInstance<T> getBean(Class<T> type) {
        return (CustomScopeInstance<T>) beans.get(type);
    }

    @Override
    public void putBean(CustomScopeInstance<?> customInstance) {
        beans.put(customInstance.getBean()
                                .getBeanClass(), customInstance);
    }

    @Override
    public void clear() {
        for (CustomScopeInstance<?> scopeInstance : beans.values()) {
            destroy(scopeInstance);
        }
        beans.clear();
    }

    private <T> void destroy(CustomScopeInstance<T> scopeInstance) {
        Bean<T> bean = scopeInstance.getBean();
        bean.destroy(scopeInstance.getInstance(), scopeInstance.getCtx());
    }

}
