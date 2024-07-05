package com.modernframework.core.utils;

/**
 * ID生成器工具类
 */
public abstract class IdUtils {

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 随机字符串
     */
    public static String fastId() {
        return UUID.fastUUID().toString().replace("-", "");
    }

}
