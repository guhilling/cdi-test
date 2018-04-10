package de.hilling.junit.cdi.jpa;

import java.time.LocalDate;
import java.time.Month;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.hilling.junit.cdi.CdiUnitRunner;

@RunWith(CdiUnitRunner.class)
public class UserServiceTest {

    @Inject
    private UserService userService;

    @Test
    public void addUser() {
        final ImmutableUser gunnar = ImmutableUser.builder()
                                                  .birthDate(LocalDate.of(1971, Month.JUNE, 15))
                                                  .firstName("Gunnar").build();
        userService.addUser(gunnar);
    }
}