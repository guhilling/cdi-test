package de.hilling.junit.cdi.jpa;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.junit.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
public class RequestScopedUserServiceTest {

    @Inject
    private RequestScopedUserService userService;
    @Inject
    private TestUtils testUtils;

    @Test
    public void addUser() {
        userService.addUser(testUtils.createGunnar());
    }
}