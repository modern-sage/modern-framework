package com.modernframework.core.convert.byteconvert;

/**
 * byte 转换 boolean
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class ByteToInteger implements ByteConvert<Integer> {

    @Override
    public Integer convert(Byte source) {
        return source != null ? source.intValue() : null;
    }
}
