package de.hilling.junit.cdi.beans;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Request {

	private UUID identifier = UUID.randomUUID();

	public UUID getIdentifier() {
		return identifier;
	}
}
