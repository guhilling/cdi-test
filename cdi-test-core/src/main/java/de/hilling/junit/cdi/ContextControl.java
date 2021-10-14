package de.hilling.junit.cdi;

import java.lang.annotation.Annotation;

public interface ContextControl {
    void startContexts();

    /**
     * Stop all container built-in Contexts and destroy all beans properly
     */
    void stopContexts();

    /**
     * Start the specified scope. This only works for scopes which are handled
     * by the CDI container itself. Custom scoped of 3rd party
     * Context implementations shall be started directly (they are portable anyway).
     *
     * @param scopeClass e.g. RequestScoped.class
     */
    void startContext(Class<? extends Annotation> scopeClass);

    /**
     * Stop the specified scope. This only works for scopes which are handled
     * by the CDI container itself. Custom scoped of 3rd party
     * Context implementations shall be stopped directly (they are portable anyway).
     *
     * @param scopeClass e.g. RequestScoped.class
     */
    void stopContext(Class<? extends Annotation> scopeClass);

}
