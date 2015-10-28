package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.OverridingServiceImpl;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(CdiUnitRunner.class)
public class InvocationTargetManagerTest {

    @Inject
    private InvocationTargetManager manager;

    private Class<CaseScopedBean> mockedClass = CaseScopedBean.class;

    @Before
    public void setUp() {
        manager.reset();
    }

    @Test
    public void noMockEnabled() {
        assertFalse(manager.isMockEnabled(mockedClass));
    }

    @Test(expected = IllegalArgumentException.class)
    public void activateNonEnabledMock() {
        manager.activateMock(mockedClass);
    }

    @Test
    public void activateAddedTest() {
        Class<?> testClass = getClass();
        manager.addAndActivateTest(testClass);
    }

    @Test
    public void activatedAddedTestAndActivateAlternative() {
        Object test = new Object() {
        };
        manager.addAndActivateTest(test.getClass());
        manager.activateAlternative(TestActivatedOverridenService.class);
        assertTrue(manager.isAlternativeEnabled(OverridingServiceImpl.class));
        assertFalse(manager.isAlternativeEnabled(test.getClass()));
        assertEquals(TestActivatedOverridenService.class, manager.alternativeFor(OverridingServiceImpl.class));
    }

    @Test
    public void cacheEnabled() {
        Person person1 = manager.mock(Person.class);
        Person person2 = manager.mock(Person.class);
        Assert.assertSame(person1, person2);
    }
}
