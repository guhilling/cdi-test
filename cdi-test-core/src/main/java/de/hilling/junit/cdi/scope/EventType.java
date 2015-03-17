package de.hilling.junit.cdi.scope;

/**
 * Unit test event type.
 */
public enum EventType {

    /**
     * Unit test is about to start.
     * <p>
     *     The event will be sent just before the test method is called.
     * </p>
     */
    STARTING,
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
     *     The test method finished and the scopes of the test are closed.
     * </p>
     */
    FINISHED
}
