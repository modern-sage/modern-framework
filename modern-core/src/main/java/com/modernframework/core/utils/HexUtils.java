package com.modernframework.core.utils;


import com.modernframework.core.codoc.Base16Codec;

/**
 * 十六进制（简写为hex或下标16）在数学中是一种逢16进1的进位制，一般用数字0到9和字母A到F表示（其中:A~F即10~15）。<br>
 * 例如十进制数57，在二进制写作111001，在16进制写作39。<br>
 * 像java,c这样的语言为了区分十六进制和十进制数值,会在十六进制数的前面加上 0x,比如0x20是十进制的32,而不是十进制的20<br>
 * <p>
 * 参考：https://my.oschina.net/xinxingegeya/blog/287476
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
