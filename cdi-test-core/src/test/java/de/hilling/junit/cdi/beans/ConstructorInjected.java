package de.hilling.junit.cdi.beans;

import javax.inject.Inject;

public class ConstructorInjected {

	private final Person person;
	private final Request request;

	@Inject
	public ConstructorInjected(Person person, Request request) {
		this.person = person;
		this.request = request;
	}

	public Person getPerson() {
		return person;
	}

	public Request getRequest() {
		return request;
	}
}
