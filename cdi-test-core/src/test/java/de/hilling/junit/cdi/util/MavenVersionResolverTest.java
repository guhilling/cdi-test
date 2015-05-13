package de.hilling.junit.cdi.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MavenVersionResolverTest {

    private MavenVersionResolver resolver = MavenVersionResolver.getInstance();
    private MavenVersion version;

    @Test
    public void resolveNoSuchArtifact() {
        resolve("org.noorg", "noartifact");
        assertNull(version);
    }

    @Test
    public void resolveSlf4j() {
        resolve("org.slf4j", "slf4j-jdk14");
        assertEquals(1, version.major);
        assertEquals(7, version.minor);
    }

    @Test
    public void resolveTwice() {
        resolve("org.slf4j", "slf4j-jdk14");
        resolve("org.slf4j", "slf4j-jdk14");
        assertEquals(1, version.major);
        assertEquals(7, version.minor);
    }

    private void resolve(String groupId, String artifactId) {
        version = resolver.getVersion(groupId, artifactId);
    }
}