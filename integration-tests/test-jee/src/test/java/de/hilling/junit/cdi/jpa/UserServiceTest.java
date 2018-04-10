package de.hilling.junit.cdi.jpa;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.hilling.junit.cdi.CdiUnitRunner;

@RunWith(CdiUnitRunner.class)
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