package de.hilling.junit.cdi.scope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.lang.annotation.Annotation;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

import org.jboss.weld.context.ApplicationContext;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundSessionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.ContextControl;
import de.hilling.junit.cdi.scopedbeans.ApplicationScopedBean;
import de.hilling.junit.cdi.scopedbeans.ConversationScopedBean;
import de.hilling.junit.cdi.scopedbeans.RequestScopedBean;
import de.hilling.junit.cdi.scopedbeans.ScopedBean;
import de.hilling.junit.cdi.scopedbeans.SessionScopedBean;

@ExtendWith(CdiTestJunitExtension.class)
class ContextControlTest {

    @Inject
    private RequestScopedBean      requestScopedBean;
    @Inject
    private ApplicationScopedBean  applicationScopedBean;
    @Inject
    private SessionScopedBean      sessionScopedBean;
    @Inject
    private ConversationScopedBean conversationScopedBean;
    @Inject
    private ContextControl         contextControl;

    @Inject
    private ApplicationContext applicationContext;
    @Inject
    private BoundSessionContext sessionContext;
    @Inject
    private BoundConversationContext conversationContext;

    @Test
    void restartRequestStopAll() {
        runTestStopAll(requestScopedBean, RequestScoped.class);
    }

    @Test
    void restartSessionStopAll() {
        runTestStopAll(sessionScopedBean, SessionScoped.class);
    }

    @Test
    void restartConversationStopAll() {
        Class<? extends Annotation> scope = RequestScoped.class;
        contextControl.stopContext(scope);
        contextControl.startContext(scope);
        UUID uuid = conversationScopedBean.getUuid();
        contextControl.stopContext(scope);
        contextControl.startContext(scope);
        UUID uuid2 = conversationScopedBean.getUuid();
        assertEquals(uuid, uuid2);
        contextControl.stopContext(ConversationScoped.class);
        contextControl.stopContext(SessionScoped.class);
        contextControl.startContext(SessionScoped.class);
        contextControl.startContext(ConversationScoped.class);

        contextControl.stopContext(ConversationScoped.class);
        contextControl.stopContext(SessionScoped.class);
        contextControl.stopContext(RequestScoped.class);
        contextControl.startContext(SessionScoped.class);
        contextControl.startContext(ConversationScoped.class);
    }

    @Test
    void startStopMultiple() {
        stopStartContext(RequestScoped.class);
        stopStartContext(ApplicationScoped.class);
        stopStartContext(ConversationScoped.class);
        stopStartContext(SessionScoped.class);
        assertFalse(sessionContext.isActive());
        assertFalse(conversationContext.isActive());
    }

    private void stopStartContext(Class<? extends Annotation> scopeClass) {
        contextControl.stopContext(scopeClass);
        contextControl.startContext(scopeClass);
        contextControl.startContext(scopeClass);
        contextControl.stopContext(scopeClass);
        contextControl.stopContext(scopeClass);
    }

    @Test
    void restartApplicationStopAll() {
        runTestStopAll(applicationScopedBean, ApplicationScoped.class);
    }

    @Test
    void restartRequest() {
        runTest(requestScopedBean, RequestScoped.class);
    }

    @Test
    void restartApplication() {
        runTest(applicationScopedBean, ApplicationScoped.class);
    }

    private void runTest(ScopedBean scopedBean, Class<? extends Annotation> scope) {
        contextControl.stopContext(scope);
        contextControl.startContext(scope);
        UUID uuid = scopedBean.getUuid();
        contextControl.stopContext(scope);
        contextControl.startContext(scope);
        UUID uuid2 = scopedBean.getUuid();
        assertNotEquals(uuid, uuid2);
    }

    private void runTestStopAll(ScopedBean scopedBean, Class<? extends Annotation> scope) {
        contextControl.stopContexts();
        contextControl.startContext(scope);
        UUID uuid = scopedBean.getUuid();
        contextControl.stopContexts();
        contextControl.startContext(scope);
        UUID uuid2 = scopedBean.getUuid();
        assertNotEquals(uuid, uuid2);
    }
}
