package com.modernframework.core.convert.localdatetimeconvert;


import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

import java.time.LocalDateTime;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public interface LocalDateTimeConvert<T> extends Converter<LocalDateTime, T> {

    @Override
    default Class<LocalDateTime> getSourceType() {
        return LocalDateTime.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), LocalDateTimeConvert.class, 0);
    }
}
