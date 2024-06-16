package com.modernframework.core.convert.shortconvert;

public class ShortToString implements ShortConverter<String> {
    @Override
    public String convert(Short source) {
        return source != null ? String.valueOf(source) : null;
    }
}
