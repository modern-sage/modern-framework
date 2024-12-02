package com.modernframework.core.lang;

import com.modernframework.core.utils.ClassLoaderUtils;
import com.modernframework.core.utils.FileUtils;
import com.modernframework.core.utils.ReflectUtils;
import com.modernframework.core.utils.URLUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * 外部Jar的类加载器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class JarClassLoader extends URLClassLoader {

    /**
     * 加载Jar到ClassPath
     *
     * @param dir jar文件或所在目录
     * @return JarClassLoader
     */
    public static JarClassLoader load(File dir) throws MalformedURLException {
        final JarClassLoader loader = new JarClassLoader();
        // 查找加载所有jar
        loader.addJar(dir);
        // 查找加载所有class
        loader.addURL(dir);
        return loader;
    }

    /**
     * 加载Jar到ClassPath
     *
     * @param jarFile jar文件或所在目录
     * @return JarClassLoader
     */
    public static JarClassLoader loadJar(File jarFile) throws MalformedURLException {
        final JarClassLoader loader = new JarClassLoader();
        loader.addJar(jarFile);
        return loader;
    }

    /**
     * 加载Jar文件到指定loader中
     *
     * @param loader  {@link URLClassLoader}
     * @param jarFile 被加载的jar
     */
    public static void loadJar(URLClassLoader loader, File jarFile) throws IOException {
        final Method method = ReflectUtils.getMethod(URLClassLoader.class, "addURL", URL.class);
        if (null != method) {
            method.setAccessible(true);
            final List<File> jars = loopJar(jarFile);
            for (File jar : jars) {
                ReflectUtils.invoke(loader, method, jar.toURI().toURL());
            }
        }
    }

    /**
     * 加载Jar文件到System ClassLoader中
     *
     * @param jarFile 被加载的jar
     * @return System ClassLoader
     */
    public static URLClassLoader loadJarToSystemClassLoader(File jarFile) throws IOException {
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        loadJar(urlClassLoader, jarFile);
        return urlClassLoader;
    }

    /**
     * 构造
     */
    public JarClassLoader() {
        this(new URL[]{});
    }

    /**
     * 构造
     *
     * @param urls 被加载的URL
     */
    public JarClassLoader(URL[] urls) {
        super(urls, ClassLoaderUtils.getClassLoader());
    }

    /**
     * 构造
     *
     * @param urls        被加载的URL
     * @param classLoader 类加载器
     */
    public JarClassLoader(URL[] urls, ClassLoader classLoader) {
        super(urls, classLoader);
    }

    /**
     * 加载Jar文件，或者加载目录
     *
     * @param jarFileOrDir jar文件或者jar文件所在目录
     * @return this
     */
    public JarClassLoader addJar(File jarFileOrDir) throws MalformedURLException {
        if (isJarFile(jarFileOrDir)) {
            return addURL(jarFileOrDir);
        }
        final List<File> jars = loopJar(jarFileOrDir);
        for (File jar : jars) {
            addURL(jar);
        }
        return this;
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    /**
     * 增加class所在目录或文件<br>
     * 如果为目录，此目录用于搜索class文件，如果为文件，需为jar文件
     *
     * @param dir 目录
     * @return this
     */
    public JarClassLoader addURL(File dir) throws MalformedURLException {
        super.addURL(URLUtils.getURL(dir));
        return this;
    }

    /**
     * 递归获得Jar文件
     *
     * @param file jar文件或者包含jar文件的目录
     * @return jar文件列表
     */
    private static List<File> loopJar(File file) {
        return FileUtils.loopFiles(file, JarClassLoader::isJarFile);
    }

    /**
     * 是否为jar文件
     *
     * @param file 文件
     * @return 是否为jar文件
     */
    private static boolean isJarFile(File file) {
        if (!FileUtils.isFile(file)) {
            return false;
        }
        return file.getPath().toLowerCase().endsWith(".jar");
    }
}
