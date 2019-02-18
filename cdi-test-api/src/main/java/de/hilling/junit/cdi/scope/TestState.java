package de.hilling.junit.cdi.scope;

/**
 * Unit test event type.
 */
public enum TestState {

    /**
     * CDI scopes are about to be started.
     * <p>
     *     The CDI scopes for the test are started next.
     * </p>
     */
    STARTING,
    /**
     * Unit test is about to start.
     * <p>
     *     The event will be sent just before the test method is called.
     *     The CDI scopes for the test are already active.
     * </p>
     */
    STARTED,
    /**
     * Unit test is finishing.
     * <p>
     *     The event will be sent after the test method has finished but before
     *     the scopes are closed.
     * </p>
     */
    FINISHING,
    /**
     * Unit test has finished.
     * <p>
     *     The test method finished and the CDI scopes of the test are closed.
     * </p>
     */
    FINISHED
}
