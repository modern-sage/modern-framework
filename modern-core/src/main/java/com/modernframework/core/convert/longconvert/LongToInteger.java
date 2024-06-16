package com.modernframework.core.convert.longconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LongToInteger implements LongConvert<Integer>{

    @Override
    public Integer convert(Long source) {
        return source != null ? source.intValue() : null;
    }
}
