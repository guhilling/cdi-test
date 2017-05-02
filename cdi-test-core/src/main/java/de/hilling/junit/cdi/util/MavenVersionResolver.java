package de.hilling.junit.cdi.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import de.hilling.junit.cdi.annotations.BypassTestInterceptor;

/**
 * Resolve maven versions from pom.properties resources in classpath.
 */
@BypassTestInterceptor
public final class MavenVersionResolver {

    private final Map<String, MavenVersion> pathToVersionMap;

    private MavenVersionResolver() {
        pathToVersionMap = new HashMap<>();
    }

    public static final MavenVersionResolver getInstance() {
        return INSTANCE;
    }

    private static final MavenVersionResolver INSTANCE = new MavenVersionResolver();

    public synchronized MavenVersion getVersion(String groupId, String artifactId) {
        String path = buildResolvePath(groupId, artifactId);
        assertCached(path);
        return pathToVersionMap.get(path);
    }

    private void assertCached(String path) {
        InputStream resourceAsStream = getClass().getResourceAsStream(path);
        if (resourceAsStream == null) {
            pathToVersionMap.put(path, null);
        } else {
            byte[] input = new byte[1024 * 10];
            try {
                resourceAsStream.read(input);
                String content = new String(input, StandardCharsets.US_ASCII);
                pathToVersionMap.put(path, new ContentParser(content).toMavenVersion());
            } catch (IOException e) {
                throw new RuntimeException("unable to load", e);
            }
        }
    }

    private String buildResolvePath(String groupId, String artifactId) {
        return "/META-INF/maven/" + groupId + "/" + artifactId + "/pom.properties";
    }

}
