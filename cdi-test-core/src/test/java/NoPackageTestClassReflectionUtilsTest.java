
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.enterprise.inject.Vetoed;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.hilling.junit.cdi.util.ReflectionsUtils;

@Vetoed
public class NoPackageTestClassReflectionUtilsTest {

    @Test
    void findOriginalClassNoPackage() {
        NoPackageTestClass testClassMock = Mockito.mock(NoPackageTestClass.class);
        assertEquals(NoPackageTestClass.class, ReflectionsUtils.getOriginalClass(testClassMock.getClass()));
    }

}
