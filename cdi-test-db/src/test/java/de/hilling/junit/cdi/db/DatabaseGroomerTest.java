package de.hilling.junit.cdi.db;

import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

public class DatabaseGroomerTest extends DbTestAbstract {

    @Inject
    private DatabaseGroomer groomer;

    @Test
    public void groomerCreated() {
         Assert.assertNotNull(groomer);
    }
}
