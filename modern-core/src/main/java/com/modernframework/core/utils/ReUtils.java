package com.modernframework.core.utils;


import com.modernframework.core.constant.PatternConstant;

import java.util.regex.Pattern;

/**
 * 正则相关工具类<br>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class ReUtils {


    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param regex   正则
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(String regex, CharSequence content) {
        if (content == null) {
            // 提供null的字符串为不匹配
            return false;
        }

        if (StringUtils.isEmpty(regex)) {
            // 正则不存在则为全匹配
            return true;
        }

        // Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Pattern pattern = PatternConstant.get(regex, Pattern.DOTALL);
        return isMatch(pattern, content);
    }


}
