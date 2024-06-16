package com.modernframework.core.convert.integerconvert;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class IntegerToString implements IntegerConverter<String> {

    @Override
    public String convert(Integer source) {
        return source != null ? String.valueOf(source) : null;
    }
}
