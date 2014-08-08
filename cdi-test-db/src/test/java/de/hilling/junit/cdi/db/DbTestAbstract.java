package de.hilling.junit.cdi.db;

import de.hilling.junit.cdi.CdiUnitRunner;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

@RunWith(CdiUnitRunner.class)
public abstract class DbTestAbstract {
    protected static final Logger LOG = Logger.getLogger(DbTestAbstract.class.getName());

}