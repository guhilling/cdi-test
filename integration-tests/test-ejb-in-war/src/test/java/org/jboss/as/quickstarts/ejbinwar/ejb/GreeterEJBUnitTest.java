package org.jboss.as.quickstarts.ejbinwar.ejb;

import de.hilling.junit.cdi.CdiUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@RunWith(CdiUnitRunner.class)
public class GreeterEJBUnitTest {

    @Inject
    private GreeterEJB greeterEJB;

    @Inject
    private EntityManager entityManager;

    @Test
    public void testSayHello() throws Exception {
        greeterEJB.sayHello("Gunnar");
        entityManager.flush();
    }
}