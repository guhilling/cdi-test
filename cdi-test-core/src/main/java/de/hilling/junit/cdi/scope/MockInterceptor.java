package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mockable
@Interceptor
@Dependent
@Priority(0)
public class MockInterceptor implements Serializable {
    private static final long serialVersionUID = 1L;

    private InvocationTargetManager invocationTargetManager = InvocationTargetManager.getInstance();

    @AroundInvoke
    public Object invokeMockableBean(InvocationContext ctx) throws Throwable {
        Class<? extends Object> javaClass = ReflectionsUtils
                .getOriginalClass(ctx.getTarget().getClass());
        if (invocationTargetManager.isAlternativeEnabled(javaClass)) {
            return callAlternative(ctx, javaClass);
        } else if (invocationTargetManager.isMockEnabled(javaClass)) {
            return callMock(ctx, javaClass);
        } else {
            return ctx.proceed();
        }
    }

    private Object callAlternative(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        try {
            Object alternative = BeanProvider.getContextualReference(invocationTargetManager.alternativeFor(javaClass));
            Method method = ctx.getMethod();
            Method alternativeMethod = alternative.getClass().getMethod(method.getName(), method.getParameterTypes());
            return alternativeMethod.invoke(alternative, ctx.getParameters());
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }

    private Object callMock(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        try {
            return ctx.getMethod().invoke(invocationTargetManager.mock(javaClass), ctx.getParameters());
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }

}
