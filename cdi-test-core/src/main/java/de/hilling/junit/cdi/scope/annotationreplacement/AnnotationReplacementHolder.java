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

/**
 * Load annotation replacements and provide them to the replacer.
 */
public class AnnotationReplacementHolder {

    public static final String DEFAULT_ANNOTATION_FILE_NAME = "cdi-test-annotations.properties";

    private static final AnnotationReplacementHolder INSTANCE;
    private Map<Class<? extends Annotation>, Annotation> replacementMap = new HashMap<>();

    public Map<Class<? extends Annotation>, Annotation> getReplacementMap() {
        return replacementMap;
    }

    protected AnnotationReplacementHolder(String annotationResourceName) {
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources(annotationResourceName);
            while (resources.hasMoreElements()) {
                addConfigurationFrom(resources.nextElement());
            }
        } catch (IOException e) {
            throw new RuntimeException("error loading annotation replacements", e);
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
                            if(method.getName().equals("annotationType")) {
                                return replacmentAnnotation;
                            } else if(method.getName().equals("hashCode")) {
                                return 0;
                            } else if(method.getName().equals("equals")) {
                                return false;
                            } else if(method.getName().equals("toString")) {
                                return replacmentAnnotation.getCanonicalName();
                            } else {
                                return null;
                            }
                        }
                    });
                    if (replacementProxy instanceof Annotation) {
                        replacementMap.put(oldAnnotation, (Annotation) replacementProxy);
                    } else {
                        throw new RuntimeException("class " + replacmentAnnotation + " is not an annotation");
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("unable to load specified class: " + e.getMessage());
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
