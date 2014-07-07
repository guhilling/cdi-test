package de.hilling.junit.cdi;

import javax.inject.Inject;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.BackendService;
import de.hilling.junit.cdi.service.SampleService;

public class MockProxyTest extends CdiTestAbstract {

	@Mock
	private BackendService backendService;

	@Inject
	private SampleService sampleService;

	@Test
	public void createPerson() {
		Person person = new Person();
		sampleService.storePerson(person);
		Mockito.verify(backendService).storePerson(person);
	}

	@Test
	public void doNothing() {
		Mockito.verifyZeroInteractions(backendService);
	}
}
