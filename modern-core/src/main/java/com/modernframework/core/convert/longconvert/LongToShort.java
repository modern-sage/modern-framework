package com.modernframework.core.convert.longconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LongToShort implements LongConvert<Short>{

    @Override
    public Short convert(Long source) {
        return source != null ? (short) source.intValue() : null;
    }
}
