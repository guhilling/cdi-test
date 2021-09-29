package de.hilling.junit.cdi.microprofile;

import org.eclipse.microprofile.config.spi.Converter;

import javax.annotation.Priority;

/**
 * Converter for properties of type Horse.
 */
@Priority(1000)
public class HorseConverter implements Converter<Horse> {
    @Override
    public Horse convert(String value) {
        return ImmutableHorse.builder().name(value).build();
    }
}
