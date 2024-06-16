package com.modernframework.core.convert.integerconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class IntegerToLong implements IntegerConverter<Long>{

    @Override
    public Long convert(Integer source) {
        return source != null ? Long.valueOf(source) : null;
    }
}
