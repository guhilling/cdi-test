package de.hilling.junit.cdi;

import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.hilling.junit.cdi.beans.StrangeName$Object;

public class ReflectionsUtilsTest {

	@Test
	public void findIdenticalClass() {
		Assert.assertEquals(String.class,
				ReflectionsUtils.getOriginalClass(String.class));
	}

	@Test
	public void findOriginalClass() {
		ReflectionsUtilsTest utilsTestMock = Mockito
				.mock(ReflectionsUtilsTest.class);
		Assert.assertEquals(ReflectionsUtilsTest.class,
				ReflectionsUtils.getOriginalClass(utilsTestMock.getClass()));
	}

	@Test(expected = RuntimeException.class)
	public void failOnStrangeClassName() {
		ReflectionsUtils.getOriginalClass(StrangeName$Object.class);
	}

}
