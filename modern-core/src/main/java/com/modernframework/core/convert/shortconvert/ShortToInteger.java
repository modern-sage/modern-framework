package com.modernframework.core.convert.shortconvert;

public class ShortToInteger  implements ShortConverter<Integer> {
    @Override
    public Integer convert(Short source) {
        return source != null ? Integer.valueOf(source) : null;
    }
}
