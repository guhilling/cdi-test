package de.hilling.cdi.sampleapp.ejb;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.cdi.sampleapp.UserRegistrationEntity;
import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class RegistrationServiceUnitTest {

    static final String NAME = "Gunnar";
    @Inject
    private RegistrationService registrationService;

    @Inject
    private EntityManager entityManager;

    private List<UserRegistrationEntity> allUsers;

    private void assertDatabaseSize(int expectedSize) {
        allUsers = entityManager.createQuery("select e from UserRegistrationEntity e", UserRegistrationEntity.class)
                                .getResultList();
        assertEquals(expectedSize, allUsers.size());
    }

    @Test
    void testSayHello() {
        assertDatabaseSize(0);
        registrationService.register(NAME);
        entityManager.flush();
        assertDatabaseSize(1);
        UserRegistrationEntity entity = allUsers.get(0);
        assertEquals("Hello " + NAME, entity.getUserName());
        assertEquals(1, entity.getId());
    }

    @Test
    void testSayHelloTwice() {
        assertDatabaseSize(0);
        registrationService.register(NAME);
        entityManager.flush();
        assertDatabaseSize(1);
    }

}