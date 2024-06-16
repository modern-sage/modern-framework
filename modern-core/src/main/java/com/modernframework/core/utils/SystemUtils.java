package com.modernframework.core.utils;

public abstract class SystemUtils {

    private static final int JAVA_VERSION_TRIM_SIZE = 3;

    /**
     * <p>
     * The <code>java.version</code> System Property. Java version number.
     * </p>
     *
     * <p>
     * Defaults to <code>null</code> if the runtime does not have
     * security access to read this property or the property does not exist.
     * </p>
     *
     * <p>
     * This value is initialized when the class is loaded. If {@link System#setProperty(String,String)} or
     * {@link System#setProperties(java.util.Properties)} is called after this class is loaded, the value
     * will be out of sync with that System property.
     * </p>
     *
     * @since Java 1.1
     */
    public static final String JAVA_VERSION = getSystemProperty("java.version");


    /**
     * 获取用户路径（绝对路径）
     */
    public static final String USER_HOME = getSystemProperty("user.home");

    /**
     * <p>
     * Gets the Java version as a <code>float</code>.
     * </p>
     *
     * <p>
     * Example return values:
     * </p>
     * <ul>
     * <li><code>1.2f</code> for Java 1.2
     * <li><code>1.31f</code> for Java 1.3.1
     * </ul>
     *
     * <p>
     * The field will return zero if {@link #JAVA_VERSION} is <code>null</code>.
     * </p>
     *
     * @since 1.0.0
     */
    public static final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();

    /**
     * <p>
     * Gets the Java version number as a <code>float</code>.
     * </p>
     *
     * <p>
     * Example return values:
     * </p>
     * <ul>
     * <li><code>1.2f</code> for Java 1.2</li>
     * <li><code>1.31f</code> for Java 1.3.1</li>
     * <li><code>1.6f</code> for Java 1.6.0_20</li>
     * </ul>
     *
     * <p>
     * Patch releases are not reported.
     * </p>
     *
     * @return the version, for example 1.31f for Java 1.3.1
     */
    private static float getJavaVersionAsFloat() {
        return toVersionFloat(toJavaVersionIntArray(SystemUtils.JAVA_VERSION, JAVA_VERSION_TRIM_SIZE));
    }

    /**
     * <p>
     * Is the Java version at least the requested version.
     * </p>
     *
     * <p>
     * Example input:
     * </p>
     * <ul>
     * <li><code>1.2f</code> to test for Java 1.2</li>
     * <li><code>1.31f</code> to test for Java 1.3.1</li>
     * </ul>
     *
     * @param requiredVersion
     *            the required version, for example 1.31f
     * @return <code>true</code> if the actual version is equal or greater than the required version
     */
    public static boolean isJavaVersionAtLeast(float requiredVersion) {
        return JAVA_VERSION_FLOAT >= requiredVersion;
    }

    /**
     * <p>
     * Gets a System property, defaulting to <code>null</code> if the property cannot be read.
     * </p>
     *
     * <p>
     * If a <code>SecurityException</code> is caught, the return value is <code>null</code> and a message is written to
     * <code>System.err</code>.
     * </p>
     *
     * @param property
     *            the system property name
     * @return the system property value or <code>null</code> if a security problem occurs
     */
    private static String getSystemProperty(String property) {
        try {
            return System.getProperty(property);
        } catch (SecurityException ex) {
            // we are not allowed to look at this property
            System.err.println("Caught a SecurityException reading the system property '" + property
                    + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }

    /**
     * <p>
     * Converts the given Java version string to an <code>int[]</code> of maximum size <code>limit</code>.
     * </p>
     *
     * <p>
     * Example return values:
     * </p>
     * <ul>
     * <li><code>[1, 2, 0]</code> for Java 1.2</li>
     * <li><code>[1, 3, 1]</code> for Java 1.3.1</li>
     * <li><code>[1, 5, 0, 21]</code> for Java 1.5.0_21</li>
     * </ul>
     *
     * @param version The string version
     * @param limit version limit
     * @return the version, for example [1, 5, 0, 21] for Java 1.5.0_21
     */
    private static int[] toJavaVersionIntArray(String version, int limit) {
        if (version == null) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        String[] strings = StringUtils.split(version, "._- ");
        int[] ints = new int[Math.min(limit, strings.length)];
        int j = 0;
        for (int i = 0; i < strings.length && j < limit; i++) {
            String s = strings[i];
            if (s.length() > 0) {
                try {
                    ints[j] = Integer.parseInt(s);
                    j++;
                } catch (Exception e) {
                }
            }
        }
        if (ints.length > j) {
            int[] newInts = new int[j];
            System.arraycopy(ints, 0, newInts, 0, j);
            ints = newInts;
        }
        return ints;
    }

    /**
     * <p>
     * Converts given the Java version array to a <code>float</code>.
     * </p>
     *
     * <p>
     * Example return values:
     * </p>
     * <ul>
     * <li><code>1.2f</code> for Java 1.2</li>
     * <li><code>1.31f</code> for Java 1.3.1</li>
     * <li><code>1.6f</code> for Java 1.6.0_20</li>
     * </ul>
     *
     * <p>
     * Patch releases are not reported.
     * </p>
     *
     * @param javaVersions The version numbers
     * @return the version, for example 1.31f for Java 1.3.1
     */
    private static float toVersionFloat(int[] javaVersions) {
        if (javaVersions == null || javaVersions.length == 0) {
            return 0f;
        }
        if (javaVersions.length == 1) {
            return javaVersions[0];
        }
        StringBuffer builder = new StringBuffer();
        builder.append(javaVersions[0]);
        builder.append('.');
        for (int i = 1; i < javaVersions.length; i++) {
            builder.append(javaVersions[i]);
        }
        try {
            return Float.parseFloat(builder.toString());
        } catch (Exception ex) {
            return 0f;
        }
    }

}
