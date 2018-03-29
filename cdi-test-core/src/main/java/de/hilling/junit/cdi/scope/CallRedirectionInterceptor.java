package de.hilling.junit.cdi.scope;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.hilling.junit.cdi.CdiRunnerException;
import de.hilling.junit.cdi.CurrentTestInformation;
import de.hilling.junit.cdi.annotations.BypassMocks;
import de.hilling.junit.cdi.util.ReflectionsUtils;

@Replaceable
@Interceptor
@Dependent
@Priority(0)
public class CallRedirectionInterceptor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Instance<InvocationTargetManager> invocationTargetManager;
    @Inject
    private Instance<CurrentTestInformation>  testInformation;

    @AroundInvoke
    public Object invokeMockableBean(InvocationContext ctx) throws Throwable {
        Class<?> javaClass = ReflectionsUtils.getOriginalClass(ctx.getTarget().getClass());
        if (invocationTargetManager.get().isAlternativeEnabled(javaClass)) {
            return callAlternative(ctx, javaClass);
        } else if (!bypassMocks() && invocationTargetManager.get().isMockEnabled(javaClass)) {
            return callMock(ctx, javaClass);
        } else {
            return ctx.proceed();
        }
    }

    private boolean bypassMocks() {
        if (testInformation.isUnsatisfied()) {
            throw new CdiRunnerException("could not find testinformation.");
        }
        final Method testMethod = testInformation.get().getMethod();
        if (testMethod != null) {
            return testMethod.isAnnotationPresent(BypassMocks.class);
        } else {
            return false;
        }
    }

    private Object callAlternative(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        try {
            Object alternative = BeanProvider
                                 .getContextualReference(invocationTargetManager.get().alternativeFor(javaClass));
            Method method = ctx.getMethod();
            Method alternativeMethod = alternative.getClass().getMethod(method.getName(), method.getParameterTypes());
            return alternativeMethod.invoke(alternative, ctx.getParameters());
        } catch (NoSuchMethodException nme) {
            return callMock(ctx, javaClass);
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }

    private Object callMock(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        try {
            return ctx.getMethod().invoke(invocationTargetManager.get().mock(javaClass), ctx.getParameters());
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }
}
