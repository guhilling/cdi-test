package de.hilling.junit.cdi.scope.annotationreplacement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import de.hilling.junit.cdi.CdiTestException;

/**
 * Load annotation replacements and provide them to the replacer.
 */
public class AnnotationReplacementHolder {

    private static final String DEFAULT_ANNOTATION_FILE_NAME = "cdi-test-annotations.properties";

    private static final AnnotationReplacementHolder                  INSTANCE;
    private final        Map<Class<? extends Annotation>, Annotation> replacementMap = new HashMap<>();

    /**
     * The replacement map contains replacement annotations (the values) for existing annotations on the {@link jakarta.enterprise.inject.spi.Annotated}
     * bean.
     *
     * @return replacement map.
     */
    Map<Class<? extends Annotation>, Annotation> getReplacementMap() {
        return replacementMap;
    }

    AnnotationReplacementHolder(String annotationResourceName) {
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources(annotationResourceName);
            while (resources.hasMoreElements()) {
                addConfigurationFrom(resources.nextElement());
            }
        } catch (IOException e) {
            throw new CdiTestException("error loading annotation replacements", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void addConfigurationFrom(URL url) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.trim().startsWith("#")) {
                    continue;
                }
                String[] split = inputLine.split(":", 2);
                try {
                    Class<? extends Annotation> oldAnnotation = (Class<? extends Annotation>) Class.forName(split[0]);
                    final Class<? extends Annotation> replacmentAnnotation = (Class<? extends Annotation>) Class.forName(split[1]);
                    final Object replacementProxy = Proxy.newProxyInstance(getClass().getClassLoader(),
                                                                           new Class[]{replacmentAnnotation},
                                                                           new AnnotationInvocationHandler(replacmentAnnotation));
                    if (replacementProxy instanceof Annotation) {
                        replacementMap.put(oldAnnotation, (Annotation) replacementProxy);
                    } else {
                        throw new CdiTestException("class " + replacmentAnnotation + " is not an annotation");
                    }
                } catch (ClassNotFoundException e) {
                    throw new CdiTestException("unable to load specified class", e);
                }
            }
        }
    }

    static class AnnotationInvocationHandler implements InvocationHandler {
        private final Class<?> replacmentAnnotation;

        AnnotationInvocationHandler(Class<?> replacmentAnnotation) {
            this.replacmentAnnotation = replacmentAnnotation;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            switch (method.getName()) {
            case "annotationType":
                return replacmentAnnotation;
            case "hashCode":
                return 0;
            case "equals":
                Object other = args[0];
                if (this == other) {
                    return true;
                } else if (!replacmentAnnotation.isInstance(other)) {
                    return false;
                } else if (Proxy.isProxyClass(other.getClass())) {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(other);
                    if (invocationHandler instanceof AnnotationReplacementHolder.AnnotationInvocationHandler) {
                        return invocationHandler.equals(this);
                    }
                }
                return false;
            case "toString":
                return replacmentAnnotation.getCanonicalName();
            default:
                return null;
            }
        }
    }

    static {
        INSTANCE = new AnnotationReplacementHolder(DEFAULT_ANNOTATION_FILE_NAME);
    }

    public static AnnotationReplacementHolder getInstance() {
        return INSTANCE;
    }
}
