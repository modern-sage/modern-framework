package com.modernframework.core.convert.mapconvert;

import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface MapConverter<T> extends Converter<Map, T> {

    @Override
    default Class<Map> getSourceType() {
        return Map.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), MapConverter.class, 0);
    }

}
