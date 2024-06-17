package com.modernframework.base.utils;

/**
 * JsonUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since  1.0.0
 */
public abstract class JsonUtils {

    public static String toJsonString(Object object) {
        return CompatibleObjectMapper.INSTANCE.writeValueAsString(object);
    }

}
