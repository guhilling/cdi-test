package de.hilling.junit.cdi;

import java.util.logging.Logger;

import org.junit.runner.RunWith;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

@RunWith(CdiMockitoRunner.class)
@TestSuiteScoped
public abstract class CdiTestAbstract {
	protected static final Logger LOG = Logger.getLogger(CdiTestAbstract.class.getName());

}