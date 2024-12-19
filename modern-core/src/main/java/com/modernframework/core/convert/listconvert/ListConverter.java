package com.modernframework.core.convert.listconvert;

import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.*;

import java.util.List;

@FunctionalInterface
public interface ListConverter<T> extends Converter<List, T> {

    @Override
    default Class<List> getSourceType() {
        return List.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), ListConverter.class, 0);
    }

}
