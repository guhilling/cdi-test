package de.hilling.junit.cdi.microprofile;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.StringUtil;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@TestSuiteScoped
public class TestConfigProducer implements Serializable {

    @Inject
    private Config config;

    static String getConfigKey(InjectionPoint ip, ConfigProperty configProperty) {
        String key = configProperty.name();
        if (!key.trim()
                .isEmpty()) {
            return key;
        }
        if (ip.getAnnotated() instanceof AnnotatedMember) {
            AnnotatedMember member = (AnnotatedMember) ip.getAnnotated();
            AnnotatedType declaringType = member.getDeclaringType();
            if (declaringType != null) {
                String[] parts = declaringType.getJavaClass()
                                              .getCanonicalName()
                                              .split("\\.");
                StringBuilder sb = new StringBuilder(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    sb.append(".")
                      .append(parts[i]);
                }
                sb.append(".")
                  .append(member.getJavaMember()
                                .getName());
                return sb.toString();
            }
        }
        throw new IllegalStateException("Could not find default name for @ConfigProperty InjectionPoint " + ip);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    String produceStringConfigProperty(InjectionPoint ip) {
        return getValue(ip, String.class);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Long getLongValue(InjectionPoint ip) {
        return getValue(ip, Long.class);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Integer getIntegerValue(InjectionPoint ip) {
        return getValue(ip, Integer.class);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Float produceFloatConfigProperty(InjectionPoint ip) {
        return getValue(ip, Float.class);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Double produceDoubleConfigProperty(InjectionPoint ip) {
        return getValue(ip, Double.class);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    Boolean produceBooleanConfigProperty(InjectionPoint ip) {
        return getValue(ip, Boolean.class);
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    <T> Optional<T> produceOptionalConfigValue(InjectionPoint injectionPoint) {
        Type type = injectionPoint.getAnnotated()
                                  .getBaseType();
        final Class<T> valueType;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            valueType = unwrapType(typeArguments[0]);
        } else {
            valueType = (Class<T>) String.class;
        }
        return Optional.ofNullable(getValue(injectionPoint, valueType));
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    <T> Set<T> producesSetConfigPropery(InjectionPoint ip) {
        Type type = ip.getAnnotated()
                      .getBaseType();
        final Class<T> valueType;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            valueType = unwrapType(typeArguments[0]);
        } else {
            valueType = (Class<T>) String.class;
        }
        HashSet<T> s = new HashSet<>();
        String stringValue = getValue(ip, String.class);
        String[] split = StringUtil.split(stringValue);
        for (int i = 0; i < split.length; i++) {
            T item = ((SmallRyeConfig) config).convert(split[i], valueType);
            s.add(item);
        }
        return s;
    }

    @GlobalTestImplementation
    @Dependent
    @Produces
    @ConfigProperty
    <T> List<T> producesListConfigPropery(InjectionPoint ip) {
        return null;
    }

    private <T> Class<T> unwrapType(Type type) {
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        return (Class<T>) type;
    }

    private <T> T getValue
            (InjectionPoint injectionPoint, Class<T> target) {
        String name = getName(injectionPoint);
        try {
            if (name == null) {
                return null;
            }
            Optional<T> optionalValue = config.getOptionalValue(name, target);
            if (optionalValue.isPresent()) {
                return optionalValue.get();
            } else {
                String defaultValue = getDefaultValue(injectionPoint);
                if (defaultValue != null && !defaultValue.equals(ConfigProperty.UNCONFIGURED_VALUE)) {
                    return ((SmallRyeConfig) config).convert(defaultValue, target);
                } else {
                    return null;
                }
            }
        } catch (RuntimeException e) {
            return null;
        }
    }

    private String getName(InjectionPoint injectionPoint) {
        for (Annotation qualifier : injectionPoint.getQualifiers()) {
            if (qualifier.annotationType()
                         .equals(ConfigProperty.class)) {
                ConfigProperty configProperty = ((ConfigProperty) qualifier);
                return getConfigKey(injectionPoint, configProperty);
            }
        }
        return null;
    }

    private String getDefaultValue(InjectionPoint injectionPoint) {
        for (Annotation qualifier : injectionPoint.getQualifiers()) {
            if (qualifier.annotationType()
                         .equals(ConfigProperty.class)) {
                return ((ConfigProperty) qualifier).defaultValue();
            }
        }
        return null;
    }

}
