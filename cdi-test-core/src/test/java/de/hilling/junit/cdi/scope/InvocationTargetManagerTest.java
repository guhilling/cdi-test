package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.OverridingServiceImpl;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvocationTargetManagerTest {

    private InvocationTargetManager manager = InvocationTargetManager.getInstance();
    private Class<CaseScopedBean> mockedClass = CaseScopedBean.class;

    @Before
    public void setUp() {
        manager.reset();
    }

    @Test
    public void noMockEnabled() {
        assertFalse(manager.isMockEnabled(mockedClass));
    }

    @Test
    public void enableMock() {
        Class<? extends InvocationTargetManagerTest> testClass = this.getClass();
        manager.addAndActivateTest(testClass);
        manager.mock(mockedClass);
        manager.activateMock(mockedClass);
        assertTrue(manager.isMockEnabled(mockedClass));
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
    public void activateAddedTestAndActivateDifferentMocks() {
        Class<?> testClass = getClass();
        manager.addAndActivateTest(testClass);
        Class<InvocationTargetManager> class1 = InvocationTargetManager.class;
        manager.activateMock(class1);
        assertTrue(manager.isMockEnabled(class1));
        Class<?> stringClass = String.class;
        manager.addAndActivateTest(stringClass);
        Class<CdiUnitRunner> class2 = CdiUnitRunner.class;
        manager.activateMock(class2);
        assertTrue(manager.isMockEnabled(class2));
        assertFalse(manager.isMockEnabled(class1));
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
