package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.service.OverridingServiceImpl;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
public class InvocationTargetManagerTest {

    private InvocationTargetManager manager;

    private Class<CaseScopedBean> mockedClass = CaseScopedBean.class;

    @Inject
    private BeanManager beanManager;

    @BeforeEach
    public void createManager() {
        manager = new InvocationTargetManager();
        try {
            ReflectionsUtils.setField(manager, beanManager, manager.getClass().getDeclaredField("beanManager"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("field not found");
        }
    }

    @Test
    public void noMockEnabled() {
        activateTest();
        assertFalse(manager.isMockEnabled(mockedClass));
    }

    @Test
    public void activateAddedTest() {
        activateTest();
    }

    private void activateTest() {
        Class<?> testClass = getClass();
        manager.activateTest(testClass);
    }

    @Test
    public void activatedAddedTestAndActivateAlternative() {
        Object test = new Object() {
        };
        manager.activateTest(test.getClass());
        manager.activateAlternative(TestActivatedOverridenService.class);
        assertTrue(manager.isAlternativeEnabled(OverridingServiceImpl.class));
        assertFalse(manager.isAlternativeEnabled(test.getClass()));
        assertEquals(TestActivatedOverridenService.class, manager.alternativeFor(OverridingServiceImpl.class));
    }

    @Test
    public void cacheEnabled() {
        activateTest();
        Person person1 = manager.mock(Person.class);
        Person person2 = manager.mock(Person.class);
        assertSame(person1, person2);
    }
}
