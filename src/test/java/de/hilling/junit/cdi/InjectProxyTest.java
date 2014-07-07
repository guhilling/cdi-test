package de.hilling.junit.cdi;

import javassist.util.proxy.ProxyFactory;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.beans.StrangeName$Object;

public class InjectProxyTest extends CdiTestAbstract {

	@Inject
	private StrangeName$Object object;
	
	@Inject
	private Person person;
	
	@Inject
	private Person.Address address;
	
	@Test
	public void testNoProxyInjection() {
		Assert.assertFalse(ProxyFactory.isProxyClass(object.getClass()));
	}
	
	@Test
	public void testProxyInjection() {
		Assert.assertTrue(ProxyFactory.isProxyClass(person.getClass()));
	}
	
	@Test
	public void testInnerClassProxyInjection() {
		Assert.assertTrue(ProxyFactory.isProxyClass(address.getClass()));
	}
	
}
