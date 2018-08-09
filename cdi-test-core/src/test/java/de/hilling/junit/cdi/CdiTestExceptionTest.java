package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CdiTestExceptionTest {

    @Test
    public void createExceptions() {
        new CdiTestException("msg");
        new CdiTestException("msg", new RuntimeException());
    }
}