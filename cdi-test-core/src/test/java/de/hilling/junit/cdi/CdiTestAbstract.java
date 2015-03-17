package de.hilling.junit.cdi;

import org.junit.runner.RunWith;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@RunWith(CdiUnitRunner.class)
public abstract class CdiTestAbstract {
    protected static final Logger LOG = Logger.getLogger(CdiTestAbstract.class.getName());

    @PostConstruct
    public void init() {
        System.out.println("created");
    }
}