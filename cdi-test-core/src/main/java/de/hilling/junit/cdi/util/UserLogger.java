package de.hilling.junit.cdi.util;

import java.util.logging.Logger;

/**
 * Logger factory to provide an instance of {@link java.util.logging.Logger} to log events like cdi-test lifecycle,
 * detected or changed beans etc.
 * <p>
 *     The logger will be named "de.hilling.cdi-test".
 * </p>
 */
public class UserLogger {
    private static final Logger CDI_TEST_LOGGER = Logger.getLogger("de.hilling.cdi-test");

    private UserLogger() {
    }

    public static Logger getInstance() {
        return CDI_TEST_LOGGER;
    }
}
