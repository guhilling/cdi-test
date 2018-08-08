package de.hilling.junit.cdi;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CdiTestJunitExtension.class)
public abstract class CdiTestAbstract {
    protected static final Logger LOG = Logger.getLogger(CdiTestAbstract.class.getName());

    @BeforeEach
    private void init() {
    }
}