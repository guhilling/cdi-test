package de.hilling.junit.cdi.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

public class ProxyMethodHandler<T> implements MethodHandler {
    private final T delegate;
    private final T mock;
    private boolean mockEnabled;

    ProxyMethodHandler(T delegate, T mock) {
        this.delegate = delegate;
        this.mock = mock;
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
}