package com.modernframework.core.convert.booleanconvert;

/**
 * BooleanToString
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class BooleanToString implements BooleanConverter<String> {

    @Override
    public String convert(Boolean source) {
        if(source == null) {
            return null;
        }
        return source.toString();
    }
}
