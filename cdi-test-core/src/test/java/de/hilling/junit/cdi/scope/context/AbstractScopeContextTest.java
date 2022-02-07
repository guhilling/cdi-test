package de.hilling.junit.cdi.scope.context;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.junit.CdiTestJunitExtension;
import de.hilling.junit.cdi.ContextControlWrapper;
import de.hilling.junit.cdi.scopedbeans.RequestScopedBean;
import de.hilling.junit.cdi.scopedbeans.ScopedBean;
import de.hilling.junit.cdi.scopedbeans.TestScopedBean;
import de.hilling.junit.cdi.scopedbeans.TestSuiteScopedBean;

@ExtendWith(CdiTestJunitExtension.class)
class AbstractScopeContextTest {

    private static Map<Type, UUID> uuidMap;

    @BeforeAll
    private static void reset() {
        uuidMap = new HashMap<>();
    }

    @Test
    void resolveInvocationTargetManagerA() {
        assertAllTypes();
    }

    @Test
    void resolveInvocationTargetManagerB() {
        assertAllTypes();
    }

    void assertAllTypes() {
        resolveOrAssert(TestSuiteScopedBean.class, Assertions::assertEquals);
        resolveOrAssert(TestScopedBean.class, Assertions::assertNotEquals);
        resolveOrAssert(RequestScopedBean.class, Assertions::assertNotEquals);
    }

    private <T extends ScopedBean> void resolveOrAssert(Class<T> type, BiConsumer<UUID, UUID> assertion) {
        ContextControlWrapper controlWrapper = ContextControlWrapper.getInstance();
        final T resolvedObject = controlWrapper.getContextualReference(type);
        if (!uuidMap.containsKey(type)) {
            uuidMap.put(type, resolvedObject.getUuid());
        } else {
            assertion.accept(uuidMap.get(type), resolvedObject.getUuid());
        }
    }

}