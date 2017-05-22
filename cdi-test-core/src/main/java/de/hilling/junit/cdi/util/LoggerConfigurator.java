package de.hilling.junit.cdi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

/**
 * Setup java.util.logging.
 */
@BypassTestInterceptor
public class LoggerConfigurator {

    public static final String LOGCONFIG_KEY = "junit.cdi.logconfig";
    public static final String LOGCONFIG_DEFAULT = "/logging.properties";

    private LoggerConfigurator() {
    }

    /**
     * Configure logging using the resource logging.properties or the file set
     * in the system property junit.cdi.logconfig
     */
    public static void configure() {
        final String configurationFile = System.getProperty(LOGCONFIG_KEY,
                LOGCONFIG_DEFAULT);
        try (InputStream inputStream = CdiUnitRunner.class
                .getResourceAsStream(configurationFile)) {
            if (inputStream == null) {
                warnLoggerNotConfigured("file not found");
            } else {
                LogManager logManager = LogManager.getLogManager();
                logManager.readConfiguration(inputStream);
            }
        } catch (final IOException e) {
            warnLoggerNotConfigured(e.getMessage());
        }
    }

    private static void warnLoggerNotConfigured(String message) {
        Logger.getAnonymousLogger().severe(
                "Could not load default logging.properties file");
        Logger.getAnonymousLogger().severe(message);
    }

}
