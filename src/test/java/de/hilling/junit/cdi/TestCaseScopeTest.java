package de.hilling.junit.cdi;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test if only one Test-Class
 * 
 * @author gunnar
 * 
 */
public class TestCaseScopeTest extends CdiTestAbstract {

	private static TestCaseScopeTest instance;

	@Test
	public void testOne() {
		assertInstanceSame();
	}

	@Test
	public void testTwo() {
		assertInstanceSame();
	}

	private void assertInstanceSame() {
		if (instance == null) {
			instance = this;
		} else {
			Assert.assertSame(this, instance);
		}
	}
}
