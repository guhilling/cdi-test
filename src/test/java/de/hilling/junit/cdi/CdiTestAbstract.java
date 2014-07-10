package de.hilling.junit.cdi;

import java.util.logging.Logger;

import org.junit.runner.RunWith;

@RunWith(CdiUnitRunner.class)
public abstract class CdiTestAbstract {
	protected static final Logger LOG = Logger.getLogger(CdiTestAbstract.class.getName());

}