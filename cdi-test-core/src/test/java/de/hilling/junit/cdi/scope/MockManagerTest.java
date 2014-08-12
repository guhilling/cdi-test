package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.beans.Person;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockManagerTest {

    private MockManager manager = MockManager.getInstance();
    private Class<CaseScopedBean> mockedClass = CaseScopedBean.class;

    @Test
    public void test() {
        assertNotNull(manager);
    }

    @Test
    public void reset() {
        manager.resetMocks();
    }

    @Test
    public void noMockEnabled() {
        assertFalse(manager.isMockEnabled(mockedClass));
    }

    @Test
    public void enableMock() {
        Class<? extends MockManagerTest> testClass = this.getClass();
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
        Class<MockManager> class1 = MockManager.class;
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
    public void cacheEnabled() {
        Person person1 = manager.mock(Person.class);
        Person person2 = manager.mock(Person.class);
        Assert.assertSame(person1, person2);
    }
}
