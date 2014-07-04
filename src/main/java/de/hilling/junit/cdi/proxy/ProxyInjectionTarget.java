package de.hilling.junit.cdi.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;

import org.mockito.Mockito;

public class ProxyInjectionTarget<X> implements InjectionTarget<X> {

	private ProxyProducer proxyProducer = new ProxyProducer();

	private final InjectionTarget<X> target;

	public ProxyInjectionTarget(InjectionTarget<X> target) {
		this.target = target;
	}

	private boolean proxyType(Class<?> clazz) {
		return clazz.getPackage().getName().startsWith("de.hilling");
	}

	@Override
	public X produce(CreationalContext<X> ctx) {
		return target.produce(ctx);
	}

	@Override
	public void dispose(X instance) {
		target.dispose(instance);
	}

	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		return target.getInjectionPoints();
	}

	@Override
	public void inject(X instance, CreationalContext<X> ctx) {
		target.inject(instance, ctx);
		for (InjectionPoint point : target.getInjectionPoints()) {
			proxyInjectionPoint(instance, ctx, point);
		}
	}

	private void proxyInjectionPoint(X instance, CreationalContext<X> ctx,
			InjectionPoint point) {
		Member member = point.getMember();
		if (member instanceof Field) {
			proxyFieldInjectionPoint(instance, ctx, member);
		}
	}

	private void proxyFieldInjectionPoint(X instance, CreationalContext<X> ctx,
			Member member) {
		Field field = (Field) member;
		try {
			Object object = field.get(instance);
			if (object != null) {
				Class<?> javaClass = object.getClass();
				if (proxyType(javaClass)) {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					Object proxy = proxyProducer.createProxy(
							javaClass,
							new ProxyMethodHandler(object, Mockito
									.mock(javaClass)));
					field.set(instance, proxy);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void postConstruct(X instance) {
		target.postConstruct(instance);
	}

	@Override
	public void preDestroy(X instance) {
		target.preDestroy(instance);
	}
}