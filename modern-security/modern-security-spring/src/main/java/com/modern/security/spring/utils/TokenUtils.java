package com.modern.security.spring.utils;

import com.modernframework.core.utils.RandomUtils;

/**
 * TokenUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public abstract class TokenUtils {

    public static String createAccessToken() {
       return RandomUtils.randomString(RandomUtils.randomInt(20, 30));
    }



}
