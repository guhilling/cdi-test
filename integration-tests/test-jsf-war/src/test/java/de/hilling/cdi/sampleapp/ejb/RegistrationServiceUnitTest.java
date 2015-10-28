package de.hilling.cdi.sampleapp.ejb;

import de.hilling.cdi.sampleapp.UserRegistrationEntity;
import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(CdiUnitRunner.class)
public class RegistrationServiceUnitTest {

    public static final String NAME = "Gunnar";
    @Inject
    private RegistrationService registrationService;

    @Inject
    private EntityManager entityManager;

    private List<UserRegistrationEntity> allUsers;

    private void assertDatabaseSize(int expectedSize) {
        allUsers = entityManager.createQuery("select e from UserRegistrationEntity e").getResultList();
        assertEquals(expectedSize, allUsers.size());
    }

    @Test
    public void testSayHello() throws Exception {
        assertDatabaseSize(0);
        registrationService.register(NAME);
        entityManager.flush();
        assertDatabaseSize(1);
        UserRegistrationEntity entity = allUsers.get(0);
        assertEquals("Hello " + NAME, entity.getUserName());
        assertEquals(1, entity.getId());
    }

    @Test
    public void testSayHelloTwice() throws Exception {
        assertDatabaseSize(0);
        registrationService.register(NAME);
        entityManager.flush();
        assertDatabaseSize(1);
    }

}