package de.hilling.junit.cdi;

import java.util.logging.LogManager;

import org.junit.jupiter.api.Assertions;
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
        LogManager logManager = LogManager.getLogManager();
        Assertions.assertEquals("ALL", logManager.getProperty("level"));
    }

    @Test
    void setUpIllegalLogging() {
        System.setProperty(LoggerConfigurator.LOGCONFIG_KEY,
                "/log4j-test.properties");
        LoggerConfigurator.configure();
        LogManager logManager = LogManager.getLogManager();
        Assertions.assertEquals("DEBUG", logManager.getProperty("level"));
    }

}
