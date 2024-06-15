package com.modernframework.core.utils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * Map相关工具类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class MapUtils {

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && false == map.isEmpty();
    }

    public static Map of(Object... values) {
        int length = values.length;
        Map map = new HashMap(length / 2);
        for (int i = 0; i < length; ) {
            map.put(values[i++], values[i++]);
        }
        return unmodifiableMap(map);
    }

}
