package de.hilling.junit.cdi.scope;

import org.junit.Assert;
import org.junit.Test;

import de.hilling.junit.cdi.beans.Person;

public class MockManagerTest {

	private MockManager manager = MockManager.getInstance();

	@Test
	public void test() {
		Assert.assertNotNull(manager);
	}

	@Test
	public void reset() {
		manager.resetMocks();
	}
	
	@Test
	public void cacheEnabled() {
		Person person1 = manager.mock(Person.class);
		Person person2 = manager.mock(Person.class);
		Assert.assertSame(person1, person2);
	}
}
