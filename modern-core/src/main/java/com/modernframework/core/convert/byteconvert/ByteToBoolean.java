package com.modernframework.core.convert.byteconvert;

/**
 * byte 转换 boolean
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class ByteToBoolean implements ByteConvert<Boolean> {

    @Override
    public Boolean convert(Byte source) {
        return source != null && source.equals(Byte.valueOf("1"));
    }
}
