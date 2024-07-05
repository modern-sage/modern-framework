package com.modernframework.core.utils;


import com.modernframework.core.codoc.Base16Codec;

/**
 * 十六进制工具
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class HexUtils {

    /**
     * 将byte值转为16进制并添加到{@link StringBuilder}中
     *
     * @param builder     {@link StringBuilder}
     * @param b           byte
     * @param toLowerCase 是否使用小写
     */
    public static void appendHex(StringBuilder builder, byte b, boolean toLowerCase) {
        (toLowerCase ? Base16Codec.CODEC_LOWER : Base16Codec.CODEC_UPPER).appendHex(builder, b);
    }

}
