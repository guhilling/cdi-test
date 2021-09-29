package de.hilling.junit.cdi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CdiTestExceptionTest {

    @Test
    void createExceptions() {
        CdiTestException plainException = new CdiTestException("msg");
        Assertions.assertNull(plainException.getCause());
        CdiTestException cdiTestException = new CdiTestException("msg", new RuntimeException("cause"));
        Assertions.assertEquals("cause", cdiTestException.getCause().getMessage());
    }
}