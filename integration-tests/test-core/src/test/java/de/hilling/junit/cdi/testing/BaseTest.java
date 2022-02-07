package de.hilling.junit.cdi.testing;

import de.hilling.junit.cdi.junit.CdiTestJunitExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
public abstract class BaseTest {

}
