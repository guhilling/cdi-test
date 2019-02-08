package de.hilling.junit.cdi;

import org.junit.jupiter.api.Test;

class CdiTestExceptionTest {

    @Test
    void createExceptions() {
        new CdiTestException("msg");
        new CdiTestException("msg", new RuntimeException());
    }
}