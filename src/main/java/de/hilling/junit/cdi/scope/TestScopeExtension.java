package de.hilling.junit.cdi.scope;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

public class TestScopeExtension implements Extension, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * add contexts after bean discovery.
	 * 
	 * @param afterBeanDiscovery
	 * @param beanManager
	 */
	public void afterBeanDiscovery(
			@Observes AfterBeanDiscovery afterBeanDiscovery,
			BeanManager beanManager) {
		addContexts(afterBeanDiscovery);
	}

	private void addContexts(AfterBeanDiscovery abd) {
		abd.addContext(new TestSuiteContext());
		abd.addContext(new TestContext());
	}

	public <X> void processBean(@Observes ProcessAnnotatedType<X> pat) {
		AnnotatedType<X> type = pat.getAnnotatedType();
		Class<X> javaClass = type.getJavaClass();
		if (javaClass.getName().startsWith("de.hilling")) {
			AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>();
			builder.readFromType(type);
			builder.addToClass(new AnnotationLiteral<Mockable>() {
				private static final long serialVersionUID = 1L;
			});
			pat.setAnnotatedType(builder.create());
		}
	}

}