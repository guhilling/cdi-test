package de.hilling.junit.cdi.scope;

import java.util.UUID;

public abstract class IdentifiablePojo {

    private final UUID identifier = UUID.randomUUID();

    public IdentifiablePojo() {
        super();
    }

    public UUID getIdentifier() {
        return identifier;
    }

}