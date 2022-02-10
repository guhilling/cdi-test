package de.hilling.junit.cdi;

import javax.enterprise.context.ApplicationScoped;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test injection with badly annotated test case.
 * Mainly provided for backward compatibility.
 */
@ExtendWith(CdiTestJunitExtension.class)
@ApplicationScoped
class BrokenScopeInjectionTest extends BaseInjectionTest {

}
