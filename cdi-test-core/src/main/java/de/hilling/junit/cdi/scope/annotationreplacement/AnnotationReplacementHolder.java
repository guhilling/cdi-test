package de.hilling.junit.cdi.scope.annotationreplacement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import de.hilling.junit.cdi.CdiTestException;

/**
 * Load annotation replacements and provide them to the replacer.
 */
public class AnnotationReplacementHolder {

    private static final String DEFAULT_ANNOTATION_FILE_NAME = "cdi-test-annotations.properties";

    private static final AnnotationReplacementHolder INSTANCE;
    private Map<Class<? extends Annotation>, Annotation> replacementMap = new HashMap<>();

    /**
     * The replacement map contains replacement annotations (the values) for existing annotations
     * on the {@link javax.enterprise.inject.spi.Annotated} bean.
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

    private void addConfigurationFrom(URL url) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.trim().startsWith("#")) {
                    continue;
                }
                String[] split = inputLine.split(":", 2);
                try {
                    Class<? extends Annotation> oldAnnotation = (Class<? extends Annotation>) Class.forName(split[0]);
                    final Class<? extends Annotation> replacmentAnnotation = (Class<? extends Annotation>) Class.forName(split[1]);
                    final Object replacementProxy = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{replacmentAnnotation}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            switch (method.getName()) {
                                case "annotationType":
                                    return replacmentAnnotation;
                                case "hashCode":
                                    return 0;
                                case "equals":
                                    return false;
                                case "toString":
                                    return replacmentAnnotation.getCanonicalName();
                                default:
                                    return null;
                            }
                        }
                    });
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

    static {
        INSTANCE = new AnnotationReplacementHolder(DEFAULT_ANNOTATION_FILE_NAME);
    }

    public static AnnotationReplacementHolder getInstance() {
        return INSTANCE;
    }
}
