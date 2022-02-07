package de.hilling.junit.cdi.junit;

import java.util.logging.Logger;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

public class CdiTestExecutionLister implements TestExecutionListener {

    private static final Logger LOG = Logger.getLogger(CdiTestExecutionLister.class.getName());


    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        testPlan.getRoots().forEach(r -> LOG.info("root: " + r));
    }

}
