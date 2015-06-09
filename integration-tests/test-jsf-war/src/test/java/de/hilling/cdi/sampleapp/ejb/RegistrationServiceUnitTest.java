package de.hilling.cdi.sampleapp.ejb;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.annotations.TestImplementation;
import de.hilling.junit.cdi.jee.EntityManagerTestProducer;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(CdiUnitRunner.class)
public class RegistrationServiceUnitTest {

    @Inject
    private RegistrationService registrationService;

    @Inject
    private EntityManager entityManager;

    @TestImplementation
    @Produces
    @Named(EntityManagerTestProducer.PERSISTENCE_UNIT_PROPERTY)
    private String persistencUnit = "cdi-test-unit-eclipselink";

    private void assertDatabaseSize(int expectedSize) {
        List list = entityManager.createQuery("select e from UserRegistrationEntity e").getResultList();
        assertEquals(expectedSize, list.size());
    }

    @Test
    public void testSayHello() throws Exception {
        assertDatabaseSize(0);
        registrationService.register("Gunnar");
        entityManager.flush();
        assertDatabaseSize(1);
    }

    @Test
    public void testSayHelloTwice() throws Exception {
        assertDatabaseSize(0);
        registrationService.register("Gunnar");
        entityManager.flush();
        assertDatabaseSize(1);
    }
}