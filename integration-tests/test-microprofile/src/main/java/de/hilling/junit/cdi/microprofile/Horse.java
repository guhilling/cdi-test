package de.hilling.junit.cdi.microprofile;

import org.immutables.value.Value;

@Value.Immutable
public interface Horse {
    String getName();
}
