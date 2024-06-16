package com.modernframework.core.convert.shortconvert;

import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

@FunctionalInterface
public interface ShortConverter<T> extends Converter<Short, T> {

    @Override
    default Class<Short> getSourceType() {
        return Short.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), ShortConverter.class, 0);
    }

}
