package com.modernframework.core.convert.timestampconvert;

import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

import java.sql.Timestamp;

@FunctionalInterface
public interface TimestampConverter<T> extends Converter<Timestamp, T> {

    @Override
    default Class<Timestamp> getSourceType() {
        return Timestamp.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), TimestampConverter.class, 0);
    }

}
