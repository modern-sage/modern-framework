package com.modernframework.core.convert.integerconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class IntegerToByte implements IntegerConverter<Byte> {

    @Override
    public Byte convert(Integer source) {
        return source != null ? source.byteValue() : null;
    }
}
