package com.modernframework.core.convert;

import com.modernframework.core.utils.TypeUtils;

@FunctionalInterface
public interface StringConverter<T> extends Converter<String, T> {

    @Override
    default Class<String> getSourceType() {
        return String.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), StringConverter.class, 0);
    }

}
