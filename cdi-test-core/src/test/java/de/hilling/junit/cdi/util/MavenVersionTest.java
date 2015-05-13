package de.hilling.junit.cdi.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MavenVersionTest {

    @Test
    public void compareDifferentMajor() {
        MavenVersion v1 = new MavenVersion(1, 2);
        MavenVersion v2 = new MavenVersion(2, 0);
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v2.compareTo(v1) > 0);
    }

    @Test
    public void compareDifferentMinor() {
        MavenVersion v1 = new MavenVersion(2, 2);
        MavenVersion v2 = new MavenVersion(2, 3);
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v2.compareTo(v1) > 0);
    }

    @Test
    public void compareAllDifferent() {
        MavenVersion v1 = new MavenVersion(1, 2);
        MavenVersion v2 = new MavenVersion(2, 3);
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v2.compareTo(v1) > 0);
    }

    @Test
    public void compareEqualVersions() {
        MavenVersion v1 = new MavenVersion(2, 2);
        MavenVersion v2 = new MavenVersion(2, 2);
        assertTrue(v1.compareTo(v2) == 0);
        assertTrue(v2.compareTo(v1) == 0);
    }
}