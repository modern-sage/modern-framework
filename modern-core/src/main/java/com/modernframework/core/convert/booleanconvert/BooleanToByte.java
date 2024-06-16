package com.modernframework.core.convert.booleanconvert;

/**
 * ShortToLocalDateTime
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class BooleanToByte implements BooleanConverter<Byte> {

    @Override
    public Byte convert(Boolean source) {
        if(source == null) {
            return null;
        }
        return source ? (byte) 1 : (byte) 0;
    }
}
