package com.modernframework.core.constant;

import java.util.regex.Pattern;

/**
 * 文件名相关工具类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class FileNameConstant {

	/**
	 * .java文件扩展名
	 */
	public static final String EXT_JAVA = ".java";
	/**
	 * .class文件扩展名
	 */
	public static final String EXT_CLASS = ".class";
	/**
	 * .jar文件扩展名
	 */
	public static final String EXT_JAR = ".jar";

	/**
	 * 类Unix路径分隔符
	 */
	public static final char UNIX_SEPARATOR = CharConstant.SLASH;
	/**
	 * Windows路径分隔符
	 */
	public static final char WINDOWS_SEPARATOR = CharConstant.BACKSLASH;

	/**
	 * Windows下文件名中的无效字符
	 */
	private static final Pattern FILE_NAME_INVALID_PATTERN_WIN = Pattern.compile("[\\\\/:*?\"<>|\r\n]");

	/**
	 * 特殊后缀
	 */
	private static final CharSequence[] SPECIAL_SUFFIX = {"tar.bz2", "tar.Z", "tar.gz", "tar.xz"};

}
