package de.hilling.junit.cdi.jpa;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
public class UserServiceTest {

    @Inject
    private UserService userService;
    @Inject
    private TestUtils testUtils;

    @Test
    public void addUser() {
        userService.addUser(testUtils.createGunnar());
    }
}