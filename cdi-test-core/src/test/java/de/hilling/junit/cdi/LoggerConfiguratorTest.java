package de.hilling.junit.cdi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.hilling.junit.cdi.util.LoggerConfigurator;

public class LoggerConfiguratorTest {

    @BeforeAll
    public void cleanUp() {
        System.clearProperty(LoggerConfigurator.LOGCONFIG_KEY);
    }

    @Test
    public void setUpDefaultLogging() {
        LoggerConfigurator.configure();
    }

    @Test
    public void setUpIllegalLogging() {
        System.setProperty(LoggerConfigurator.LOGCONFIG_KEY,
                "/no_such_resource");
        LoggerConfigurator.configure();
    }

}
