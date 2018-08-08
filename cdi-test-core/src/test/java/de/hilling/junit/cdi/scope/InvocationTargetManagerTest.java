package de.hilling.junit.cdi.scope;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.OverridingServiceImpl;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;

@ExtendWith(CdiTestJunitExtension.class)
public class InvocationTargetManagerTest {

    @Inject
    private InvocationTargetManager manager;

    private Class<CaseScopedBean> mockedClass = CaseScopedBean.class;

    @BeforeEach
    public void setUp() {
        manager.reset();
    }

    @Test
    public void noMockEnabled() {
        assertFalse(manager.isMockEnabled(mockedClass));
    }

    @Test
    public void activateNonEnabledMock() {
        assertThrows(IllegalArgumentException.class, () -> manager.activateMock(mockedClass));
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
        assertSame(person1, person2);
    }
}
