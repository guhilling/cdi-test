package de.hilling.junit.cdi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test injections with proper junit test case.
 */
@ExtendWith(CdiTestJunitExtension.class)
class InjectionTest extends BaseInjectionTest {

}
