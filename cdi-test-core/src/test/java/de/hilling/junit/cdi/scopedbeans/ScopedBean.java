package de.hilling.junit.cdi.scopedbeans;

import java.util.UUID;

public class ScopedBean {
	
	protected UUID uuid;

	public ScopedBean() {
		uuid = UUID.randomUUID();
	}

	public UUID getUuid() {
		return uuid;
	}

}