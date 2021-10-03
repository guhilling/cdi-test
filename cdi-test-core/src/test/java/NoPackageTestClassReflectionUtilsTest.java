
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.hilling.junit.cdi.util.ReflectionsUtils;

public class NoPackageTestClassReflectionUtilsTest {

    @Test
    void findOriginalClassNoPackage() {
        NoPackageTestClass testClassMock = Mockito.mock(NoPackageTestClass.class);
        assertEquals(NoPackageTestClass.class, ReflectionsUtils.getOriginalClass(testClassMock.getClass()));
    }

}
