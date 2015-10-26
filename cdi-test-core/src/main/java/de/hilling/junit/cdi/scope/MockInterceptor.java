package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.util.ReflectionsUtils;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Mockable
@Interceptor
@Dependent
@Priority(0)
public class MockInterceptor implements Serializable {
    private static final long serialVersionUID = 1L;

    private MockManager mockManager = MockManager.getInstance();

    @AroundInvoke
    public Object invokeMockableBean(InvocationContext ctx) throws Throwable {
        Class<? extends Object> javaClass = ReflectionsUtils
                .getOriginalClass(ctx.getTarget().getClass());
        if (mockManager.isMockEnabled(javaClass)) {
            return callMock(ctx, javaClass);
        } else {
            return ctx.proceed();
        }
    }

    private Object callMock(InvocationContext ctx, Class<?> javaClass) throws Throwable {
        try {
            return ctx.getMethod().invoke(mockManager.mock(javaClass), ctx.getParameters());
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
    }

}
