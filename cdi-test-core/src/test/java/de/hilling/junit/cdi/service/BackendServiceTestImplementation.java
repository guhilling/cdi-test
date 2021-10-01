package de.hilling.junit.cdi.service;

import de.hilling.junit.cdi.BackendServiceException;
import de.hilling.junit.cdi.annotations.ActivatableTestImplementation;
import de.hilling.junit.cdi.beans.Person;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestState;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.enterprise.event.Observes;
import java.lang.reflect.InvocationTargetException;

@ActivatableTestImplementation
public class BackendServiceTestImplementation extends BackendService {

    private int invocations;

    private RuntimeException exceptionToThrow;

    @Override
    public String storePerson(Person person) {
        if (exceptionToThrow != null) {
            throw exceptionToThrow;
        } else {
            invocations++;
        }
        return null;
    }

    protected void testStarted(@Observes @TestEvent(TestState.STARTING) ExtensionContext context) {
        context.getTestMethod().ifPresent(m -> {
            final BackendServiceException serviceException = m.getAnnotation(BackendServiceException.class);
            if (serviceException != null) {
                try {
                    exceptionToThrow = serviceException.value()
                                                       .getDeclaredConstructor()
                                                       .newInstance();
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("creating test exception failed", e);
                }
            }
        });
    }

    public int getInvocations() {
        return invocations;
    }
}
