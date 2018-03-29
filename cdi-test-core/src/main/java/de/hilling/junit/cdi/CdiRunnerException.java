package de.hilling.junit.cdi;

/**
 * Exception to mark unexpected errors during test run. Any occurence of this exception should
 * be due to an implementation error in cdi-test or an error in the test setup.
 */
public class CdiRunnerException extends RuntimeException {

    public CdiRunnerException(String message) {
        super(message);
    }

    public CdiRunnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
