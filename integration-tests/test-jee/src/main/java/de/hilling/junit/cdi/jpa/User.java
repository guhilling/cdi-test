package de.hilling.junit.cdi.jpa;

import java.time.LocalDate;

import org.immutables.value.Value;

@Value.Immutable
public interface User {
    String getFirstName();

    LocalDate getBirthDate();
}
