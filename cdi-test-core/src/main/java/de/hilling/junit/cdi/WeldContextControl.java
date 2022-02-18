package de.hilling.junit.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.jboss.weld.context.ApplicationContext;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.jboss.weld.context.bound.BoundSessionContext;
import org.jboss.weld.context.bound.MutableBoundRequest;

/**
 * Wrapper around the weld-se specific context methods.
 * <p>
 *     Originally taken from the Deltaspike project.
 * </p>
 */
@Dependent
public class WeldContextControl implements ContextControl {
    private static final ThreadLocal<WeldContextControl.RequestContextHolder> requestContexts = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Object>>                     sessionMaps     = new ThreadLocal<>();


    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private BoundSessionContext sessionContext;

    @Inject
    private Instance<BoundRequestContext> requestContextFactory;

    @Inject
    private BoundConversationContext conversationContext;


    @Override
    public void startContexts() {
        startApplicationScope();
        startSessionScope();
        startRequestScope();
        startConversationScope();
    }

    @Override
    public void startContext(Class<? extends Annotation> scopeClass) {
        if (scopeClass.isAssignableFrom(ApplicationScoped.class)) {
            startApplicationScope();
        } else if (scopeClass.isAssignableFrom(SessionScoped.class)) {
            startSessionScope();
        } else if (scopeClass.isAssignableFrom(RequestScoped.class)) {
            startRequestScope();
        } else if (scopeClass.isAssignableFrom(ConversationScoped.class)) {
            startConversationScope();
        }
    }

    /**
     * Stops Conversation, Request and Session contexts. Does NOT stop Application context, only invalidates App scoped beans, as in Weld this context
     * always active and clears automatically on shutdown.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void stopContexts() {
        stopConversationScope();
        stopRequestScope();
        stopSessionScope();
        stopApplicationScope();
    }

    @Override
    public void stopContext(Class<? extends Annotation> scopeClass) {
        if (scopeClass.isAssignableFrom(ApplicationScoped.class)) {
            stopApplicationScope();
        } else if (scopeClass.isAssignableFrom(SessionScoped.class)) {
            stopSessionScope();
        } else if (scopeClass.isAssignableFrom(RequestScoped.class)) {
            stopRequestScope();
        } else if (scopeClass.isAssignableFrom(ConversationScoped.class)) {
            stopConversationScope();
        }
    }

    /*
     * This is a no-op method. In Weld Application Context is active as soon as the container starts
     */
    private void startApplicationScope() {
        // No-op, in Weld Application context is always active
    }

    /**
     * Weld Application context is active from container start to its shutdown This method merely clears out all ApplicationScoped beans BUT the
     * context will still be active which may result in immediate re-creation of some beans.
     */
    private void stopApplicationScope() {
        // Welds ApplicationContext gets cleaned at shutdown.
        // Weld App context should be always active
        if (applicationContext.isActive()) {
            // destroys the bean instances, but the context stays active
            applicationContext.invalidate();
        }
    }

    void startRequestScope() {
        WeldContextControl.RequestContextHolder rcHolder = requestContexts.get();
        if (rcHolder == null) {
            rcHolder = new WeldContextControl.RequestContextHolder(requestContextFactory.get(), new HashMap<>());
            requestContexts.set(rcHolder);
            rcHolder.getBoundRequestContext().associate(rcHolder.getRequestMap());
            rcHolder.getBoundRequestContext().activate();
        }
    }

    void stopRequestScope() {
        WeldContextControl.RequestContextHolder rcHolder = requestContexts.get();
        if (rcHolder != null && rcHolder.getBoundRequestContext().isActive()) {
            rcHolder.getBoundRequestContext().invalidate();
            rcHolder.getBoundRequestContext().deactivate();
            rcHolder.getBoundRequestContext().dissociate(rcHolder.getRequestMap());
            requestContexts.remove();
        }
    }

    private void startSessionScope() {
        Map<String, Object> sessionMap = sessionMaps.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<>();
            sessionMaps.set(sessionMap);
        }

        sessionContext.associate(sessionMap);
        sessionContext.activate();

    }

    private void stopSessionScope() {
        if (sessionContext.isActive()) {
            sessionContext.invalidate();
            sessionContext.deactivate();
            sessionContext.dissociate(sessionMaps.get());

            sessionMaps.remove();
        }
    }

    void startConversationScope() {
        WeldContextControl.RequestContextHolder rcHolder = requestContexts.get();
        if (rcHolder == null) {
            startRequestScope();
            rcHolder = requestContexts.get();
        }
        conversationContext.associate(new MutableBoundRequest(rcHolder.requestMap, sessionMaps.get()));
        conversationContext.activate();
    }

    void stopConversationScope() {
        WeldContextControl.RequestContextHolder rcHolder = requestContexts.get();
        if (rcHolder == null) {
            startRequestScope();
            rcHolder = requestContexts.get();
        }
        if (conversationContext.isActive()) {
            conversationContext.invalidate();
            conversationContext.deactivate();
            conversationContext.dissociate(new MutableBoundRequest(rcHolder.getRequestMap(), sessionMaps.get()));
        }
    }


    private static class RequestContextHolder {
        private final BoundRequestContext boundRequestContext;
        private final Map<String, Object> requestMap;

        private RequestContextHolder(BoundRequestContext boundRequestContext, Map<String, Object> requestMap) {
            this.boundRequestContext = boundRequestContext;
            this.requestMap = requestMap;
        }

        public BoundRequestContext getBoundRequestContext() {
            return boundRequestContext;
        }

        public Map<String, Object> getRequestMap() {
            return requestMap;
        }
    }

}
