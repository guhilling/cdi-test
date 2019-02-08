package de.hilling.junit.cdi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.util.LoggerConfigurator;

class LoggerConfiguratorTest {

    @BeforeEach
    void cleanUp() {
        System.clearProperty(LoggerConfigurator.LOGCONFIG_KEY);
    }

    @Test
    void setUpDefaultLogging() {
        LoggerConfigurator.configure();
    }

    @Test
    void setUpIllegalLogging() {
        System.setProperty(LoggerConfigurator.LOGCONFIG_KEY,
                "/no_such_resource");
        LoggerConfigurator.configure();
    }

}
