package com.modernframework.core.convert.shortconvert;

public class ShortToLong implements ShortConverter<Long> {
    @Override
    public Long convert(Short source) {
        return source != null ? Long.valueOf(source) : null;
    }
}
