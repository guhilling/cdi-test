package de.hilling.junit.cdi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse content of pom.properties file.
 */
public class ContentParser {

    private static final Pattern PATTERN;

    static {
        PATTERN = Pattern.compile("version=(.*)$", Pattern.MULTILINE);
    }

    private MavenVersion version;

    public ContentParser(String content) {
        Matcher matcher = PATTERN.matcher(content);
        if (matcher.find()) {
            String versionString = matcher.group(1);
            String[] numbers = versionString.split("\\.");
            if (numbers.length < 2) {
                version = null;
            } else {
                version = new MavenVersion(Integer.valueOf(numbers[0]), Integer.valueOf(numbers[1]));
            }
        } else {
            version = null;
        }
    }

    public MavenVersion toMavenVersion() {
        return version;
    }
}
