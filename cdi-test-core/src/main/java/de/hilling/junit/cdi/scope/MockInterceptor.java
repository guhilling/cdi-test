package de.hilling.junit.cdi.scope;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import de.hilling.junit.cdi.util.ReflectionsUtils;

@Mockable
@Interceptor
@Dependent
@Priority(0)
public class MockInterceptor implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean initialized = false;
	private Object mock;

	private MockManager mockManager = MockManager.getInstance();

	@AroundInvoke
	public Object invokeMockableBean(InvocationContext ctx) throws Throwable {
		Class<? extends Object> javaClass = ReflectionsUtils
				.getOriginalClass(ctx.getTarget().getClass());
		if (!initialized) {
			initialized = true;
			mock = mockManager.mock(javaClass);
		}
		if (mockManager.isMockEnabled(javaClass)) {
			return callMock(ctx);
		} else {
			return ctx.proceed();
		}
	}

	private Object callMock(InvocationContext ctx) throws Throwable {
		try {
			return ctx.getMethod().invoke(mock, ctx.getParameters());
		} catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
	}

}
