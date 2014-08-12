package de.hilling.junit.cdi;

import de.hilling.junit.cdi.util.LoggerConfigurator;
import org.junit.After;
import org.junit.Test;

public class LoggerConfiguratorTest {

    @After
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
