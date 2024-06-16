package com.modernframework.core.utils;

import java.net.URL;

/**
 * Resource资源工具类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class ResourceUtils {


    /**
     * 获得资源相对路径对应的URL
     *
     * @param resource  资源相对路径，{@code null}和""都表示classpath根路径
     * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
     * @return {@link URL}
     */
    public static URL getResource(String resource, Class<?> baseClass) {
        resource = StringUtils.nullToEmpty(resource);
        return (null != baseClass) ? baseClass.getResource(resource) : ClassLoaderUtils.getClassLoader().getResource(resource);
    }

    /**
     * 获得资源的URL<br>
     * 路径用/分隔，例如:
     *
     * <pre>
     * config/a/db.config
     * spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     */
    public static URL getResource(String resource) {
        return getResource(resource, null);
    }

}
