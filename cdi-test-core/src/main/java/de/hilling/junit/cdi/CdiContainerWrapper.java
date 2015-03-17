package de.hilling.junit.cdi;

import de.hilling.junit.cdi.scope.MockManager;
import de.hilling.junit.cdi.util.ReflectionsUtils;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.mockito.Mock;

import java.lang.reflect.Field;

/**
 * Wrapper for handling cdi startup, shutdown and lifecycle.
 */
public class CdiContainerWrapper {
    private static CdiContainer cdiContainer;
    static ContextControl contextControl;

    private CdiContainerWrapper() {
    }

    public static void startCdiContainer() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        contextControl = cdiContainer.getContextControl();
    }

    static void assignMocks(Object test) {
        for (Field field : ReflectionsUtils.getAllFields(test.getClass())) {
            if (field.isAnnotationPresent(Mock.class)) {
                assignMockAndActivateProxy(field, test);
            }
        }
    }

    private static void assignMockAndActivateProxy(Field field, Object test) {
        field.setAccessible(true);
        try {
            Class<?> type = field.getType();
            Object mock = MockManager.getInstance().mock(type);
            field.set(test, mock);
            MockManager.getInstance().activateMock(type);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

}
