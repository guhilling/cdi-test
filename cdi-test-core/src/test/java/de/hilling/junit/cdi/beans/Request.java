package de.hilling.junit.cdi.beans;

import jakarta.enterprise.context.RequestScoped;
import java.util.UUID;

@RequestScoped
public class Request {

    private UUID identifier = UUID.randomUUID();

    public UUID getIdentifier() {
        return identifier;
    }
}
