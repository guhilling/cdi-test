package de.hilling.junit.cdi.scope;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.service.OverridingServiceImpl;
import de.hilling.junit.cdi.service.TestActivatedOverridenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
class InvocationTargetManagerTest {

    private InvocationTargetManager manager;

    private final Class<CaseScopedBean> mockedClass = CaseScopedBean.class;

    @Inject
    private BeanManager beanManager;

    @Inject
    private TestInformation testInformation;

    @BeforeEach
    void createManager() {
        manager = new InvocationTargetManager(beanManager, testInformation);
    }

    @Test
    void noMockEnabled() {
        assertFalse(manager.isMockEnabled(mockedClass));
    }

    @Test
    void activatedAddedTestAndActivateAlternative() {
        manager.activateAlternative(TestActivatedOverridenService.class);
        assertTrue(manager.isAlternativeEnabled(OverridingServiceImpl.class));
        assertEquals(TestActivatedOverridenService.class, manager.alternativeFor(OverridingServiceImpl.class));
    }

}
