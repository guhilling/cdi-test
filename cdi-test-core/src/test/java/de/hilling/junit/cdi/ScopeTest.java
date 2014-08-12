package de.hilling.junit.cdi;

import de.hilling.junit.cdi.beans.Request;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.UUID;

public class ScopeTest extends CdiTestAbstract {

    @Inject
    private Request request;

    private UUID lastIdentifier;

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(request);
    }

    @Test
    public void testOne() {
        assertNotEqual();
    }

    @Test
    public void testTwo() {
        assertNotEqual();
    }

    private void assertNotEqual() {
        if (lastIdentifier == null) {
            request.getIdentifier();
        } else {
            Assert.assertNotEquals(lastIdentifier, request.getIdentifier());
        }
    }

}
