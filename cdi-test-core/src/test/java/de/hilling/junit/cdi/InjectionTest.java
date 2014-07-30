package de.hilling.junit.cdi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;

import de.hilling.junit.cdi.beans.ConstructorInjected;
import de.hilling.junit.cdi.beans.Person;

public class InjectionTest extends CdiTestAbstract {

	@Inject
	private Person person;

	@Inject
	private ConstructorInjected constructorInjected;

	@Test
	public void testInjection() {
		assertNotNull(person);
		assertNotNull(constructorInjected);
	}

	@Test
	public void testProxiedCostructorInjection() {
		assertNotNull(constructorInjected.getPerson());
		assertNotNull(constructorInjected.getRequest());
	}

	@Test
	public void testPersons() {
		checkPersonWorks(person);
		checkPersonWorks(constructorInjected.getPerson());
	}

	private void checkPersonWorks(Person person) {
		person.setName("test");
		assertEquals("test", person.getName());
	}

}
