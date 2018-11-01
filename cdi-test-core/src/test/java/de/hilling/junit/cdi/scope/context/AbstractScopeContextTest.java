package de.hilling.junit.cdi.scope.context;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.scopedbeans.RequestScopedBean;
import de.hilling.junit.cdi.scopedbeans.ScopedBean;
import de.hilling.junit.cdi.scopedbeans.TestScopedBean;
import de.hilling.junit.cdi.scopedbeans.TestSuiteScopedBean;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@ExtendWith(CdiTestJunitExtension.class)
public class AbstractScopeContextTest {

    private static Map<Type, UUID> uuidMap;

    @BeforeAll
    private static void reset() {
        uuidMap = new HashMap<>();
    }

    @Test
    public void resolveInvocationTargetManagerA() {
        assertAllTypes();
    }

    @Test
    public void resolveInvocationTargetManagerB() {
        assertAllTypes();
    }

    public void assertAllTypes() {
        resolveOrAssert(TestSuiteScopedBean.class, Assertions::assertEquals);
        resolveOrAssert(TestScopedBean.class, Assertions::assertNotEquals);
        resolveOrAssert(RequestScopedBean.class, Assertions::assertNotEquals);
    }

    @SuppressWarnings("unchecked")
    private <T extends ScopedBean> void resolveOrAssert(Class<T> type, BiConsumer<UUID, UUID> assertion) {
        final T resolvedObject = BeanProvider.getContextualReference(type, false);
        if (!uuidMap.containsKey(type)) {
            uuidMap.put(type, resolvedObject.getUuid());
        } else {
            assertion.accept(uuidMap.get(type), resolvedObject.getUuid());
        }
    }

}