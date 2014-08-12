package de.hilling.junit.cdi.db;

import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

public class ConnectionAnalyzerTest extends DbTestAbstract {

    @Inject
    private ConnectionAnalyzer analyzer;

    @Test
    public void analyzerCreated() {
        Assert.assertNotNull(analyzer);
    }

}
