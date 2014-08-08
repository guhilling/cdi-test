package de.hilling.junit.cdi.db;

import org.junit.Assert;
import org.junit.Test;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.sql.Connection;

public class DatabaseCleanerTest extends DbTestAbstract {

    @Inject
    private DatabaseCleaner groomer;

    @Test
    public void groomerCreated() {
        Assert.assertNotNull(groomer);
    }

}
