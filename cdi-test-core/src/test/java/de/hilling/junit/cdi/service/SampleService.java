package de.hilling.junit.cdi.service;

import javax.inject.Inject;

import de.hilling.junit.cdi.beans.Person;

public class SampleService {

	@Inject
	private BackendService backendService;
	
	public void storePerson(Person person) {
		backendService.storePerson(person);
	}
}
