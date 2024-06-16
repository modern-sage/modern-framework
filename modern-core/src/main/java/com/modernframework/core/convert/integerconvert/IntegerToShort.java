package com.modernframework.core.convert.integerconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */

public class IntegerToShort implements IntegerConverter<Short>{

    @Override
    public Short convert(Integer source) {
        return source != null ? (short) source.intValue() : null;
    }
}
