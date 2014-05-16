package de.hilling.junit.cdi;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;

import de.hilling.junit.cdi.beans.Person;

public class InjectionTest extends CdiTestAbstract {

	@Inject
	private Person person;

	@Test
	public void testInjection() {
		assertNotNull(person);
	}
	
}
