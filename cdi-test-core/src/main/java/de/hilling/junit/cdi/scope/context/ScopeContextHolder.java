package de.hilling.junit.cdi.scope.context;

import java.util.Map;

/**
 * An interface to provide the capability to create a custom
 * context holder. The context holder is a cache for beans.
 * Via an implementation of this interface the user can define the
 * storage of beans of the specific context.
 * <p>
 * Context classes use implementations of this holder and provide
 * beans to this holder.
 * </p>
 */
public interface ScopeContextHolder {
    /**
     * Store bean
     *
     * @param customInstance container for bean, context and instance
     */
    void putBean(CustomScopeInstance<?> customInstance);

    /**
     * Receive all instantiated beans of the specific context.
     *
     * @return a map of all beans of the specific context
     */
    Map<Class<?>, CustomScopeInstance> getBeans();

    /**
     * Receive one bean of the specific context.
     *
     * @param type the class of the bean
     * @param <T>  the type of the bean
     * @return the container (@see CustomScopeInstance) for the bean
     */
    <T> CustomScopeInstance<T> getBean(Class<T> type);

    /**
     * Clear the cache and call destroy on every bean.
     */
    void clear();
}
