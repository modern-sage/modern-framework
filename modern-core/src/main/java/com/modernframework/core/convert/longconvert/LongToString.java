package com.modernframework.core.convert.longconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LongToString implements LongConvert<String>{

    @Override
    public String convert(Long source) {
        return source != null ? String.valueOf(source) : null;
    }
}
