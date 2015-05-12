package org.jboss.as.quickstarts.ejbinwar.ejb;

import de.hilling.junit.cdi.CdiUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(CdiUnitRunner.class)
public class GreeterEJBUnitTest {

    @Inject
    private GreeterEJB greeterEJB;

    @Inject
    private EntityManager entityManager;

    private void assertDatabaseSize(int expectedSize) {
        List list = entityManager.createQuery("select e from GreetingEntity e").getResultList();
        assertEquals(expectedSize, list.size());
    }

    @Test
    public void testSayHello() throws Exception {
        assertDatabaseSize(0);
        greeterEJB.sayHello("Gunnar");
        entityManager.flush();
        assertDatabaseSize(1);
    }

    @Test
    public void testSayHelloTwice() throws Exception {
        assertDatabaseSize(0);
        greeterEJB.sayHello("Gunnar");
        entityManager.flush();
        assertDatabaseSize(1);
    }
}