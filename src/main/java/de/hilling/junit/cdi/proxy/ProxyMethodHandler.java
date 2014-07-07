package de.hilling.junit.cdi.proxy;

import java.lang.reflect.Method;

import de.hilling.junit.cdi.scope.MockManager;
import javassist.util.proxy.MethodHandler;

public class ProxyMethodHandler<T> implements MethodHandler {
    private final T delegate;
    private final T mock;
    private boolean mockEnabled;

    @SuppressWarnings("unchecked")
	public ProxyMethodHandler(T delegate) {
        this.delegate = delegate;
        this.mock = (T) MockManager.getInstance().mock(delegate.getClass());
    }

    public Object invoke(Object self, Method proxiedMethod, Method proceed,
                         Object[] args)
    throws Throwable {
        if (mockEnabled) {
            return proxiedMethod.invoke(mock, args);
        } else {
            return proxiedMethod.invoke(delegate, args);
        }
    }

    public boolean isMockEnabled() {
        return mockEnabled;
    }

    public void setMockEnabled(boolean mockEnabled) {
        this.mockEnabled = mockEnabled;
    }

	public T getDelegate() {
		return delegate;
	}

	public T getMock() {
		return mock;
	}
}