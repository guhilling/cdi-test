package de.hilling.junit.cdi.testing;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
abstract class BaseTest {

}
