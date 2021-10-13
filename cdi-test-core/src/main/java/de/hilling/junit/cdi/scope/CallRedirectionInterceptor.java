package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.ContextControlWrapper;
import de.hilling.junit.cdi.util.ReflectionsUtils;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.deltaspike.core.api.provider.BeanProvider;

@Replaceable
@Interceptor
@Dependent
@Priority(0)
public class CallRedirectionInterceptor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Instance<InvocationTargetManager> invocationTargetManager;

    @AroundInvoke
    public Object invokeMockableBean(InvocationContext ctx) throws Throwable {
        Class<?> javaClass = ReflectionsUtils.getOriginalClass(ctx.getTarget().getClass());
        if (invocationTargetManager.get().isAlternativeEnabled(javaClass)) {
            return callAlternative(ctx, javaClass);
        } else if (invocationTargetManager.get().isMockEnabled(javaClass)) {
            return callMock(ctx, javaClass);
        } else {
            return ctx.proceed();
        }
    }

    @SuppressWarnings("squid:S00112")
    private Object callAlternative(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        Method method = ctx.getMethod();
        ContextControlWrapper controlWrapper = ContextControlWrapper.getInstance();
        Object alternative = controlWrapper.getContextualInstance(invocationTargetManager.get().alternativeFor(javaClass));
        Object alternativeOld = BeanProvider
                .getContextualReference(invocationTargetManager.get().alternativeFor(javaClass));

        try {
            Method alternativeMethod = alternative.getClass().getMethod(method.getName(), method.getParameterTypes());
            return alternativeMethod.invoke(alternative, ctx.getParameters());
        } catch (NoSuchMethodException nme) {
            throw new IllegalStateException("method " + method.getName() + " not found on alternative " + alternative);
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }

    @SuppressWarnings("squid:S00112")
    private Object callMock(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        try {
            return ctx.getMethod().invoke(invocationTargetManager.get().mock(javaClass), ctx.getParameters());
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }
}
