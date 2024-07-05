package com.modernframework.core.utils;

import com.modernframework.core.constant.StrConstant;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.modernframework.core.constant.StrConstant.COMMA;
import static com.modernframework.core.constant.StrConstant.EMPTY_JSON;
import static java.lang.String.valueOf;

/**
 * 字符串工具类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class StringUtils {

    public static final int INDEX_NOT_FOUND = -1;

    public static final String EMPTY = "";

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 字符串常量：{@code "null"} <br>
     * 注意：{@code "null" != null}
     */
    public static final String NULL = "null";

    /**
     * 下划线字符
     */
    public static final char UNDERLINE = '_';

    // ------------------------------------------------------------------------ str

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    public static int length(String value) {
        return value == null ? 0 : value.length();
    }

    /**
     * 切分字符串，不限制分片数量
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> split(String str, String separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, -1, isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串
     *
     * @param str         被切分的字符串
     * @param separator   分隔符字符
     * @param limit       限制分片数，-1不限制
     * @param isTrim      是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (isAnyBlank(str, separator)) {
            return Collections.emptyList();
        }
        if (isTrim) {
            str = str.trim();
        }
        String[] strings = str.split(separator);
        List<String> result = Arrays.stream(strings)
                .filter(x -> !ignoreEmpty || !isEmpty(x))
                .collect(Collectors.toList());

        if(limit > -1) {
            return result.subList(0, limit);
        } else {
            return result;
        }
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数，-1不限制
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, String separator, int limit) {
        return split(str, separator, limit, true, true);
    }

    /**
     * 切分字符串，去除切分后每个元素两边的空白符，去除空白项
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> splitTrim(String str, String separator) {
        return splitTrim(str, separator, -1);
    }

    public static String[] split(String value, char delimiter) {
        return split(value, valueOf(delimiter));
    }

    public static String[] split(String value, String delimiter) {
        if (isEmpty(value)) {
            return EMPTY_STRING_ARRAY;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(value, delimiter);
        return (String[]) ArrayUtils.asArray(stringTokenizer, String.class);
    }

    /**
     * <p>字符串是否为空白，空白的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     *     <li>空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     */
    public static boolean isBlank(CharSequence str) {
        final int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtils.isBlankChar(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 字符串列表是否任意有空白
     *
     * @return 若存在空白，则返回 true
     */
    public static boolean isAnyBlank(Collection<CharSequence> strList) {
        if (CollectionUtils.isEmpty(strList)) {
            return true;
        }
        for (CharSequence str : strList) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串列表是否任意有空白
     *
     * @return 若存在空白，则返回 true
     */
    public static boolean isAnyBlank(CharSequence... strs) {
        return isAnyBlank(Arrays.asList(strs));
    }

    /**
     * 字符串列表是否任意有空白
     *
     * @return 若存在空白，则返回 true
     */
    public static boolean isAllBlank(Collection<CharSequence> strList) {
        if (CollectionUtils.isEmpty(strList)) {
            return true;
        }
        for (CharSequence str : strList) {
            if (!isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串列表是否任意有空白
     *
     * @return 若存在空白，则返回 true
     */
    public static boolean isAllBlank(CharSequence... strs) {
        return isAllBlank(Arrays.asList(strs));
    }


    /**
     * <p>字符串是否为非空白，非空白的定义如下： </p>
     * <ol>
     *     <li>不为 {@code null}</li>
     *     <li>不为空字符串：{@code ""}</li>
     *     <li>不为空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     */
    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * <p>字符串是否为空，空的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     * </ol>
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>字符串是否为非空白，非空白的定义如下： </p>
     * <ol>
     *     <li>不为 {@code null}</li>
     *     <li>不为空字符串：{@code ""}</li>
     * </ol>
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    // ------------------------------------------------------------------------ equals

    /**
     * 比较两个字符串（大小写敏感）。
     *
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     *
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 比较两个字符串是否相等，规则如下
     * <ul>
     *     <li>str1和str2都为{@code null}</li>
     *     <li>忽略大小写使用{@link String#equalsIgnoreCase(String)}判断相等</li>
     *     <li>不忽略大小写使用{@link String#contentEquals(CharSequence)}判断相等</li>
     * </ul>
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }


    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String param) {
        if (isBlank(param)) {
            return EMPTY;
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 首字母转换小写
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String lowerFirst(String param) {
        if (isBlank(param)) {
            return EMPTY;
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }

    // ------------------------------------------------------------------------ convert str

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String convertToUtf8Str(Object obj) {
        return convertStr(obj, StandardCharsets.UTF_8);
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String convertStr(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return convertStr((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return convertStr((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return convertStr((ByteBuffer) obj, charset);
        } else if (ArrayUtils.isArray(obj)) {
            return ArrayUtils.toString(obj);
        }

        return obj.toString();
    }


    // ------------------------------------------------------------------------ replace

    /**
     * 替换字符串中的指定字符串，忽略大小写
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacement 被替换的字符串
     * @return 替换后的字符串
     */
    public static String replaceIgnoreCase(CharSequence str, CharSequence searchStr, CharSequence replacement) {
        return replace(str, 0, searchStr, replacement, true);
    }

    /**
     * 替换字符串中的指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacement 被替换的字符串
     * @return 替换后的字符串
     */
    public static String replace(CharSequence str, CharSequence searchStr, CharSequence replacement) {
        return replace(str, 0, searchStr, replacement, false);
    }

    /**
     * 替换字符串中的指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacement 被替换的字符串
     * @param ignoreCase  是否忽略大小写
     * @return 替换后的字符串
     */
    public static String replace(CharSequence str, CharSequence searchStr, CharSequence replacement, boolean ignoreCase) {
        return replace(str, 0, searchStr, replacement, ignoreCase);
    }

    /**
     * 替换字符串中的指定字符串
     *
     * @param str         字符串
     * @param fromIndex   开始位置（包括）
     * @param searchStr   被查找的字符串
     * @param replacement 被替换的字符串
     * @param ignoreCase  是否忽略大小写
     * @return 替换后的字符串
     */
    public static String replace(CharSequence str, int fromIndex, CharSequence searchStr, CharSequence replacement, boolean ignoreCase) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return strOrNull(str);
        }
        if (null == replacement) {
            replacement = EMPTY;
        }

        final int strLength = str.length();
        final int searchStrLength = searchStr.length();
        if (strLength < searchStrLength) {
            // issue#I4M16G@Gitee
            return strOrNull(str);
        }

        if (fromIndex > strLength) {
            return strOrNull(str);
        } else if (fromIndex < 0) {
            fromIndex = 0;
        }

        final StringBuilder result = new StringBuilder(strLength - searchStrLength + replacement.length());
        if (0 != fromIndex) {
            result.append(str.subSequence(0, fromIndex));
        }

        int preIndex = fromIndex;
        int index;
        while ((index = indexOf(str, searchStr, preIndex, ignoreCase)) > -1) {
            result.append(str.subSequence(preIndex, index));
            result.append(replacement);
            preIndex = index + searchStrLength;
        }

        if (preIndex < strLength) {
            // 结尾部分
            result.append(str.subSequence(preIndex, strLength));
        }
        return result.toString();
    }

    /**
     * 替换指定字符串的指定区间内字符为指定字符串，字符串只重复一次<br>
     * 此方法使用{@link String#codePoints()}完成拆分替换
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @param replacedStr  被替换的字符串
     * @return 替换后的字符串
     */
    public static String replace(CharSequence str, int startInclude, int endExclude, CharSequence replacedStr) {
        if (isEmpty(str)) {
            return strOrNull(str);
        }
        final String originalStr = strOrNull(str);
        int[] strCodePoints = originalStr.codePoints().toArray();
        final int strLength = strCodePoints.length;
        if (startInclude > strLength) {
            return originalStr;
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            return originalStr;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < startInclude; i++) {
            stringBuilder.append(new String(strCodePoints, i, 1));
        }
        stringBuilder.append(replacedStr);
        for (int i = endExclude; i < strLength; i++) {
            stringBuilder.append(new String(strCodePoints, i, 1));
        }
        return stringBuilder.toString();
    }

    /**
     * 替换字符串中最后一个指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacedStr 被替换的字符串
     * @return 替换后的字符串
     */
    public static String replaceLast(CharSequence str, CharSequence searchStr, CharSequence replacedStr) {
        return replaceLast(str, searchStr, replacedStr, false);
    }

    /**
     * 替换字符串中最后一个指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacedStr 被替换的字符串
     * @param ignoreCase  是否忽略大小写
     * @return 替换后的字符串
     */
    public static String replaceLast(CharSequence str, CharSequence searchStr, CharSequence replacedStr, boolean ignoreCase) {
        if (isEmpty(str)) {
            return strOrNull(str);
        }
        int lastIndex = lastIndexOf(str, searchStr, str.length(), ignoreCase);
        if (INDEX_NOT_FOUND == lastIndex) {
            return strOrNull(str);
        }
        return replace(str, lastIndex, searchStr, replacedStr, ignoreCase);
    }

    /**
     * 替换字符串中第一个指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacedStr 被替换的字符串
     * @return 替换后的字符串
     */
    public static String replaceFirst(CharSequence str, CharSequence searchStr, CharSequence replacedStr) {
        return replaceFirst(str, searchStr, replacedStr, false);
    }

    /**
     * 替换字符串中第一个指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacedStr 被替换的字符串
     * @param ignoreCase  是否忽略大小写
     * @return 替换后的字符串
     */
    public static String replaceFirst(CharSequence str, CharSequence searchStr, CharSequence replacedStr, boolean ignoreCase) {
        if (isEmpty(str)) {
            return strOrNull(str);
        }
        int startInclude = indexOf(str, searchStr, 0, ignoreCase);
        if (INDEX_NOT_FOUND == startInclude) {
            return strOrNull(str);
        }
        return replace(str, startInclude, startInclude + searchStr.length(), replacedStr);
    }

    /**
     * 替换指定字符串的指定区间内字符为"*"
     * 俗称：脱敏功能，后面其他功能，可以见：DesensitizedUtil(脱敏工具类)
     *
     * <pre>
     * StringUtils.hide(null,*,*)=null
     * StringUtils.hide("",0,*)=""
     * StringUtils.hide("jackduan@163.com",-1,4)   ****duan@163.com
     * StringUtils.hide("jackduan@163.com",2,3)    ja*kduan@163.com
     * StringUtils.hide("jackduan@163.com",3,2)    jackduan@163.com
     * StringUtils.hide("jackduan@163.com",16,16)  jackduan@163.com
     * StringUtils.hide("jackduan@163.com",16,17)  jackduan@163.com
     * </pre>
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @return 替换后的字符串
     */
    public static String hide(CharSequence str, int startInclude, int endExclude) {
        return replace(str, startInclude, endExclude, "*");
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String strOrNull(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 调用对象的toString方法，null会返回{@code null}
     *
     * @param obj 对象
     * @return 字符串 or {@code null}
     */
    public static String toStringOrNull(Object obj) {
        return null == obj ? null : obj.toString();
    }

    // ------------------------------------------------------------------------ indexOf

    /**
     * 指定范围内查找指定字符
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @return 位置
     */
    public static int indexOf(CharSequence str, char searchChar) {
        return indexOf(str, searchChar, 0);
    }

    /**
     * 指定范围内查找指定字符
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @param start      起始位置，如果小于0，从0开始查找
     * @return 位置
     */
    public static int indexOf(CharSequence str, char searchChar, int start) {
        if (str instanceof String) {
            return ((String) str).indexOf(searchChar, start);
        } else {
            return indexOf(str, searchChar, start, -1);
        }
    }

    /**
     * 指定范围内查找指定字符
     *
     */
    public static int indexOf(CharSequence text, char searchChar, int start, int end) {
        return indexOf(text, new StringBuilder(searchChar), start, end, false);
    }

    /**
     * 指定范围内查找字符串，忽略大小写<br>
     *
     */
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    /**
     * 指定范围内查找字符串
     *
     */
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int fromIndex) {
        return indexOf(str, searchStr, fromIndex, true);
    }

    /**
     * 指定范围内查找字符串
     *
     */
    public static int indexOf(CharSequence text, CharSequence searchStr, int from, int end, boolean ignoreCase) {
        return indexOf(text, searchStr, from, end, ignoreCase, false);
    }

    /**
     * 指定范围内查找字符串
     *
     */
    public static int indexOf(CharSequence text, CharSequence searchStr, int from, int end, boolean ignoreCase, boolean reverse) {
        if (isEmpty(text) || isEmpty(searchStr)) {
            if (text.equals(searchStr)) {
                return 0;
            } else {
                return INDEX_NOT_FOUND;
            }
        }

        int position = INDEX_NOT_FOUND;
        // 终止位置，如果超过str.length()则默认查找到字符串末尾
        end = end > text.length() - 1 ? text.length() : end;

        // 外部遍历下标
        int i = reverse ? end : from;

        for (; ; ) {
            if (reverse && i <= from) {
                break;
            } else if (!reverse && i >= end) {
                break;
            }

            // 默认为匹配
            boolean match = true;
            for (int j = 0; j < searchStr.length(); j++) {
                int textIndex = 0;
                if (reverse) {
                    // 逆向
                    textIndex = (end + 1 - searchStr.length())/*逆向的开始位置*/ + j;
                } else {
                    // 正常顺序
                    textIndex = i + j;
                }
                if (ignoreCase) {
                    if (Character.toLowerCase(text.charAt(textIndex)) != Character.toLowerCase(searchStr.charAt(j))) {
                        // 内层不满足，跳出，外层进入下一个循环
                        match = false;
                        break;
                    }
                } else {
                    if (text.charAt(textIndex) != searchStr.charAt(j)) {
                        // 内层不满足，跳出，外层进入下一个循环
                        match = false;
                        break;
                    }
                }
            }

            // 内层走完，条件符合，定位下表，跳出外部循环
            if (match) {
                position = i;
                break;
            }

            // 外部循环的下一步
            if (reverse) {
                --i;
            } else {
                ++i;
            }
        }

        return position;
    }

    /**
     * 指定范围内查找字符串
     *
     */
    public static int indexOf(CharSequence text, CharSequence searchStr, int from, boolean ignoreCase) {
        return indexOf(text, searchStr, from, Integer.MAX_VALUE, ignoreCase);
    }

    /**
     * 指定范围内查找字符串，忽略大小写
     *
     */
    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return lastIndexOfIgnoreCase(str, searchStr, str.length());
    }

    /**
     * 指定范围内查找字符串，忽略大小写<br>
     * fromIndex 为搜索起始位置，从后往前计数
     *
     * @param str       字符串
     * @param searchStr 需要查找位置的字符串
     * @param fromIndex 起始位置，从后往前计数
     * @return 位置
     */
    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int fromIndex) {
        return lastIndexOf(str, searchStr, fromIndex, true);
    }

    /**
     * 指定范围内查找字符串<br>
     * fromIndex 为搜索起始位置，从后往前计数
     *
     * @param text       字符串
     * @param searchStr  需要查找位置的字符串
     * @param from       起始位置，从后往前计数
     * @param ignoreCase 是否忽略大小写
     * @return 位置
     */
    public static int lastIndexOf(CharSequence text, CharSequence searchStr, int from, boolean ignoreCase) {
        return indexOf(text, searchStr, from, Integer.MAX_VALUE, ignoreCase, true);
    }

    /**
     * 返回字符串 searchStr 在字符串 str 中第 ordinal 次出现的位置。
     *
     * <p>
     * 如果 str=null 或 searchStr=null 或 ordinal&ge;0 则返回-1<br>
     * 此方法来自：Apache-Commons-Lang
     * <p>
     *
     * @param str       被检查的字符串，可以为null
     * @param searchStr 被查找的字符串，可以为null
     * @param ordinal   第几次出现的位置
     * @return 查找到的位置
     */
    public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        if (str == null || searchStr == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.isEmpty()) {
            return 0;
        }
        int found = 0;
        int index = INDEX_NOT_FOUND;
        do {
            index = indexOf(str, searchStr, index + 1, false);
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < ordinal);
        return index;
    }

    // ------------------------------------------------------------------------ contains

    /**
     * 指定字符是否在字符串中出现过
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @return 是否包含
     */
    public static boolean contains(CharSequence str, char searchChar) {
        return indexOf(str, searchChar) > -1;
    }

    /**
     * 指定字符串是否在字符串中出现过
     *
     * @param str       字符串
     * @param searchStr 被查找的字符串
     * @return 是否包含
     */
    public static boolean contains(CharSequence str, CharSequence searchStr) {
        if (null == str || null == searchStr) {
            return false;
        }
        return str.toString().contains(searchStr);
    }

    /**
     * 是否包含特定字符，忽略大小写，如果给定两个参数都为{@code null}，返回true
     *
     * @param str     被检测字符串
     * @param testStr 被测试是否包含的字符串
     * @return 是否包含
     */
    public static boolean containsIgnoreCase(CharSequence str, CharSequence testStr) {
        if (null == str) {
            // 如果被监测字符串和
            return null == testStr;
        }
        return indexOfIgnoreCase(str, testStr) > -1;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串<br>
     * 忽略大小写
     *
     * @param str      指定字符串
     * @param testStrs 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAnyIgnoreCase(CharSequence str, CharSequence... testStrs) {
        if (ArrayUtils.isEmpty(testStrs)) {
            return false;
        }
        return Arrays.stream(testStrs).anyMatch(x -> containsIgnoreCase(str, x));
    }


    /**
     * 如果字符串是{@code null}或者&quot;&quot;或者空白，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * blankToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * blankToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
     * blankToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;default&quot;
     * blankToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String blankToDefault(CharSequence str, String defaultStr) {
        return isBlank(str) ? defaultStr : str.toString();
    }

    /**
     * 当给定字符串为空字符串时，转换为{@code null}
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(CharSequence str) {
        return isEmpty(str) ? null : str.toString();
    }

    /**
     * 当给定字符串为null时，转换为Empty
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(CharSequence str) {
        return nullToDefault(str, EMPTY);
    }

    /**
     * 如果字符串是 {@code null}，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * nullToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * nullToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
     * nullToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * nullToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(CharSequence str, String defaultStr) {
        return (str == null) ? defaultStr : str.toString();
    }

    /**
     * 反转字符串<br>
     * 例如：abcd =》dcba
     *
     * @param str 被反转的字符串
     * @return 反转后的字符串
     */
    public static String reverse(String str) {
        return new String(ArrayUtils.reverse(str.toCharArray()));
    }

    /**
     * 首字母大写
     */
    public static String toUpperCaseFirstOne(String str) {
        StringBuilder sb = new StringBuilder(str);
        return toUpperCaseFirstOne(sb);
    }

    /**
     * 首字母大写
     */
    public static String toUpperCaseFirstOne(StringBuilder sb) {
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * 首字母小写
     */
    public static String toLowerCaseFirstOne(String str) {
        StringBuilder sb = new StringBuilder(str);
        return toLowerCaseFirstOne(sb);
    }

    /**
     * 首字母小写
     */
    public static String toLowerCaseFirstOne(StringBuilder sb) {
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

    // ------------------------------------------------------------------------ Trim

    /**
     * 除去字符串头尾部的空白，如果字符串是{@code null}，依然返回{@code null}。
     *
     * <p>
     * 注意，和{@link String#trim()}不同，此方法使用{@link CharUtils#isBlankChar(char)} 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * <pre>
     * trim(null)          = null
     * trim(&quot;&quot;)            = &quot;&quot;
     * trim(&quot;     &quot;)       = &quot;&quot;
     * trim(&quot;abc&quot;)         = &quot;abc&quot;
     * trim(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去头尾空白的字符串，如果原字串为{@code null}，则返回{@code null}
     */
    public static String trim(CharSequence str) {
        return (null == str) ? null : trim(str, 0);
    }

    /**
     * 压缩字符串
     * 1. 去除 \r \n
     * 2. 去除空格
     */
    public static String compress(String str) {
        return Optional.ofNullable(str)
                .map(x -> x.replace("\r", ""))
                .map(x -> x.replace("\\r", ""))
                .map(x -> x.replace("\n", ""))
                .map(x -> x.replace("\\n", ""))
                .map(x -> x.replace(" ", ""))
                .orElse(null);
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是{@code null}，返回{@code ""}。
     *
     * <pre>
     * StrUtil.trimToEmpty(null)          = ""
     * StrUtil.trimToEmpty("")            = ""
     * StrUtil.trimToEmpty("     ")       = ""
     * StrUtil.trimToEmpty("abc")         = "abc"
     * StrUtil.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str 字符串
     * @return 去除两边空白符后的字符串, 如果为null返回""
     */
    public static String trimToEmpty(CharSequence str) {
        return str == null ? EMPTY : trim(str);
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是{@code null}或者""，返回{@code null}。
     *
     * <pre>
     * StrUtil.trimToNull(null)          = null
     * StrUtil.trimToNull("")            = null
     * StrUtil.trimToNull("     ")       = null
     * StrUtil.trimToNull("abc")         = "abc"
     * StrUtil.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str 字符串
     * @return 去除两边空白符后的字符串, 如果为空返回null
     */
    public static String trimToNull(CharSequence str) {
        final String trimStr = trim(str);
        return EMPTY.equals(trimStr) ? null : trimStr;
    }

    /**
     * 除去字符串头部的空白，如果字符串是{@code null}，则返回{@code null}。
     *
     * <p>
     * 注意，和{@link String#trim()}不同，此方法使用{@link CharUtils#isBlankChar(char)} 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * <pre>
     * trimStart(null)         = null
     * trimStart(&quot;&quot;)           = &quot;&quot;
     * trimStart(&quot;abc&quot;)        = &quot;abc&quot;
     * trimStart(&quot;  abc&quot;)      = &quot;abc&quot;
     * trimStart(&quot;abc  &quot;)      = &quot;abc  &quot;
     * trimStart(&quot; abc &quot;)      = &quot;abc &quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为{@code null}或结果字符串为{@code ""}，则返回 {@code null}
     */
    public static String trimStart(CharSequence str) {
        return trim(str, -1);
    }

    /**
     * 除去字符串尾部的空白，如果字符串是{@code null}，则返回{@code null}。
     *
     * <p>
     * 注意，和{@link String#trim()}不同，此方法使用{@link CharUtils#isBlankChar(char)} 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     *
     * <pre>
     * trimEnd(null)       = null
     * trimEnd(&quot;&quot;)         = &quot;&quot;
     * trimEnd(&quot;abc&quot;)      = &quot;abc&quot;
     * trimEnd(&quot;  abc&quot;)    = &quot;  abc&quot;
     * trimEnd(&quot;abc  &quot;)    = &quot;abc&quot;
     * trimEnd(&quot; abc &quot;)    = &quot; abc&quot;
     * </pre>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为{@code null}或结果字符串为{@code ""}，则返回 {@code null}
     */
    public static String trimEnd(CharSequence str) {
        return trim(str, 1);
    }

    /**
     * 除去字符串头尾部的空白符，如果字符串是{@code null}，依然返回{@code null}。
     *
     * @param str  要处理的字符串
     * @param mode {@code -1}表示trimStart，{@code 0}表示trim全部， {@code 1}表示trimEnd
     * @return 除去指定字符后的的字符串，如果原字串为{@code null}，则返回{@code null}
     */
    public static String trim(CharSequence str, int mode) {
        return trim(str, mode, CharUtils::isBlankChar);
    }

    /**
     * 按照断言，除去字符串头尾部的断言为真的字符，如果字符串是{@code null}，依然返回{@code null}。
     *
     * @param str       要处理的字符串
     * @param mode      {@code -1}表示trimStart，{@code 0}表示trim全部， {@code 1}表示trimEnd
     * @param predicate 断言是否过掉字符，返回{@code true}表述过滤掉，{@code false}表示不过滤
     * @return 除去指定字符后的的字符串，如果原字串为{@code null}，则返回{@code null}
     */
    public static String trim(CharSequence str, int mode, Predicate<Character> predicate) {
        String result;
        if (str == null) {
            result = null;
        } else {
            int length = str.length();
            int start = 0;
            int end = length;// 扫描字符串头部
            if (mode <= 0) {
                while ((start < end) && (predicate.test(str.charAt(start)))) {
                    start++;
                }
            }// 扫描字符串尾部
            if (mode >= 0) {
                while ((start < end) && (predicate.test(str.charAt(end - 1)))) {
                    end--;
                }
            }
            if ((start > 0) || (end < length)) {
                result = str.toString().substring(start, end);
            } else {
                result = str.toString();
            }
        }

        return result;
    }

    /**
     * 字符串是否以给定字符开始
     *
     * @param str 字符串
     * @param c   字符
     * @return 是否开始
     */
    public static boolean startWith(CharSequence str, char c) {
        if (isEmpty(str)) {
            return false;
        }
        return c == str.charAt(0);
    }


    /**
     * 是否以指定字符串开头，忽略大小写
     *
     * @param str    被监测字符串
     * @param prefix 开头字符串
     * @return 是否以指定字符串开头
     */
    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, true);
    }

    /**
     * 是否以指定字符串开头<br>
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param str        被监测字符串
     * @param prefix     开头字符串
     * @param ignoreCase 是否忽略大小写
     * @return 是否以指定字符串开头
     */
    public static boolean startWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
        return startWith(str, prefix, ignoreCase, false);
    }

    /**
     * 是否以指定字符串开头<br>
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false<br>
     * <pre>
     *     CharSequenceUtil.startWith("123", "123", false, true);   -- false
     *     CharSequenceUtil.startWith("ABCDEF", "abc", true, true); -- true
     *     CharSequenceUtil.startWith("abc", "abc", true, true);    -- false
     * </pre>
     *
     * @param str          被监测字符串
     * @param prefix       开头字符串
     * @param ignoreCase   是否忽略大小写
     * @param ignoreEquals 是否忽略字符串相等的情况
     * @return 是否以指定字符串开头
     */
    public static boolean startWith(CharSequence str, CharSequence prefix, boolean ignoreCase, boolean ignoreEquals) {
        if (null == str || null == prefix) {
            if (ignoreEquals) {
                return false;
            }
            return null == str && null == prefix;
        }

        boolean isStartWith = str.toString()
                .regionMatches(ignoreCase, 0, prefix.toString(), 0, prefix.length());

        if (isStartWith) {
            return (!ignoreEquals) || (!equals(str, prefix, ignoreCase));
        }
        return false;
    }

    // ------------------------------------------------------------------------ endWith

    /**
     * 字符串是否以给定字符结尾
     *
     * @param str 字符串
     * @param c   字符
     * @return 是否结尾
     */
    public static boolean endWith(CharSequence str, char c) {
        if (isEmpty(str)) {
            return false;
        }
        return c == str.charAt(str.length() - 1);
    }

    /**
     * 是否以指定字符串结尾<br>
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param str        被监测字符串
     * @param suffix     结尾字符串
     * @param ignoreCase 是否忽略大小写
     * @return 是否以指定字符串结尾
     */
    public static boolean endWith(CharSequence str, CharSequence suffix, boolean ignoreCase) {
        return endWith(str, suffix, ignoreCase, false);
    }

    /**
     * 是否以指定字符串结尾<br>
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param str          被监测字符串
     * @param suffix       结尾字符串
     * @param ignoreCase   是否忽略大小写
     * @param ignoreEquals 是否忽略字符串相等的情况
     * @return 是否以指定字符串结尾
     */
    public static boolean endWith(CharSequence str, CharSequence suffix, boolean ignoreCase, boolean ignoreEquals) {
        if (null == str || null == suffix) {
            if (ignoreEquals) {
                return false;
            }
            return null == str && null == suffix;
        }

        final int strOffset = str.length() - suffix.length();
        boolean isEndWith = str.toString()
                .regionMatches(ignoreCase, strOffset, suffix.toString(), 0, suffix.length());

        if (isEndWith) {
            return (!ignoreEquals) || (!equals(str, suffix, ignoreCase));
        }
        return false;
    }

    /**
     * 是否以指定字符串结尾
     *
     * @param str    被监测字符串
     * @param suffix 结尾字符串
     * @return 是否以指定字符串结尾
     */
    public static boolean endWith(CharSequence str, CharSequence suffix) {
        return endWith(str, suffix, false);
    }

    /**
     * 是否以指定字符串结尾，忽略大小写
     *
     * @param str    被监测字符串
     * @param suffix 结尾字符串
     * @return 是否以指定字符串结尾
     */
    public static boolean endWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return endWith(str, suffix, true);
    }

    /**
     * 给定字符串是否以任何一个字符串结尾<br>
     * 给定字符串和数组为空都返回false
     *
     * @param str      给定字符串
     * @param suffixes 需要检测的结尾字符串
     * @return 给定字符串是否以任何一个字符串结尾
     */
    public static boolean endWithAny(CharSequence str, CharSequence... suffixes) {
        if (isEmpty(str) || ArrayUtils.isEmpty(suffixes)) {
            return false;
        }

        for (CharSequence suffix : suffixes) {
            if (endWith(str, suffix, false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给定字符串是否以任何一个字符串结尾（忽略大小写）<br>
     * 给定字符串和数组为空都返回false
     *
     * @param str      给定字符串
     * @param suffixes 需要检测的结尾字符串
     * @return 给定字符串是否以任何一个字符串结尾
     */
    public static boolean endWithAnyIgnoreCase(CharSequence str, CharSequence... suffixes) {
        if (isEmpty(str) || ArrayUtils.isEmpty(suffixes)) {
            return false;
        }

        for (CharSequence suffix : suffixes) {
            if (endWith(str, suffix, true)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     */
    public static String removeAll(String str, char... chars) {
        if (null == str || ArrayUtils.isEmpty(chars)) {
            return str;
        }
        final int len = str.length();
        if (0 == len) {
            return str;
        }
        final StringBuilder builder = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!ArrayUtils.contains(chars, c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    // ------------------------------------------------------------------------ sub

    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str              String
     * @param fromIndexInclude 开始的index（包括）
     * @param toIndexExclude   结束的index（不包括）
     * @return 字串
     */
    public static String sub(String str, int fromIndexInclude, int toIndexExclude) {
        if (isEmpty(str)) {
            return str;
        }
        int len = str.length();

        if (fromIndexInclude < 0) {
            fromIndexInclude = len + fromIndexInclude;
            if (fromIndexInclude < 0) {
                fromIndexInclude = 0;
            }
        } else if (fromIndexInclude > len) {
            fromIndexInclude = len;
        }

        if (toIndexExclude < 0) {
            toIndexExclude = len + toIndexExclude;
            if (toIndexExclude < 0) {
                toIndexExclude = len;
            }
        } else if (toIndexExclude > len) {
            toIndexExclude = len;
        }

        if (toIndexExclude < fromIndexInclude) {
            int tmp = fromIndexInclude;
            fromIndexInclude = toIndexExclude;
            toIndexExclude = tmp;
        }

        if (fromIndexInclude == toIndexExclude) {
            return EMPTY;
        }

        return str.substring(fromIndexInclude, toIndexExclude);
    }

    /**
     * 切割指定位置之后部分的字符串
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后后剩余的后半部分字符串
     */
    public static String subSuf(String string, int fromIndex) {
        if (isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br>
     * 例如：str=setName, preLength=3 =》 return name
     *
     * @param str       被处理的字符串
     * @param preLength 去掉的长度
     * @return 处理后的字符串，不符合规范返回null
     */
    public static String removePreAndLowerFirst(CharSequence str, int preLength) {
        if (str == null) {
            return null;
        }
        if (str.length() > preLength) {
            char first = Character.toLowerCase(str.charAt(preLength));
            if (str.length() > preLength + 1) {
                return first + str.toString().substring(preLength + 1);
            }
            return String.valueOf(first);
        } else {
            return str.toString();
        }
    }

    /**
     * 忽略大小写去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
     */
    public static String removePrefixIgnoreCase(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str(str);
        }

        final String str2 = str.toString();
        if (startWithIgnoreCase(str, prefix)) {
            return subSuf(str2, prefix.length());// 截取后半段
        }
        return str2;
    }

    /**
     * 清理空白字符
     *
     * @param str 被清理的字符串
     * @return 清理后的字符串
     */
    public static String cleanBlank(CharSequence str) {
        return filter(str, c -> !CharUtils.isBlankChar(c));
    }

    // ------------------------------------------------------------------------ filter

    /**
     * 过滤字符串
     *
     * @param str    字符串
     * @param filter 过滤器，{@link Predicate#test(Object)} (Object)}返回为{@code true}的保留字符
     * @return 过滤后的字符串
     */
    public static String filter(CharSequence str, final Predicate<Character> filter) {
        if (str == null || filter == null) {
            return str(str);
        }

        int len = str.length();
        final StringBuilder sb = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (filter.test(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String join(String[] strings) {
        return join(Collections.singletonList(strings), COMMA);
    }

    public static String join(String[] strings, String separator) {
        return join(Collections.singletonList(strings), separator);
    }

    public static String join(Collection<?> collection) {
        return join(Collections.singletonList(collection), COMMA);
    }

    public static String join(Collection<?> collection, String separator) {
        if (collection == null) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Object[] objects = collection.toArray();

            for(int i = 0; i < collection.size() - 1; ++i) {
                stringBuilder.append(objects[i].toString()).append(separator);
            }

            if (!collection.isEmpty()) {
                stringBuilder.append(objects[collection.size() - 1]);
            }

            return stringBuilder.toString();
        }
    }
}
