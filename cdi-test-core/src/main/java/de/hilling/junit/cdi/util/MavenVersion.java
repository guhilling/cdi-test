package de.hilling.junit.cdi.util;

/**
 * Version for requested artifact.
 */
public final class MavenVersion implements Comparable<MavenVersion> {
    /**
     * Maven major version.
     */
    public final int major;
    /**
     * Maven minor version.
     */
    public final int minor;

    @Override
    public int compareTo(MavenVersion other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.major == other.major) {
            return this.minor - other.minor;
        } else {
            return this.major - other.major;
        }
    }

    public MavenVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MavenVersion that = (MavenVersion) o;

        return major==that.major && minor==that.minor;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        return result;
    }
}