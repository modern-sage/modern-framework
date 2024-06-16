package com.modernframework.core.convert.longconvert;


import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
@FunctionalInterface
public interface LongConvert<T> extends Converter<Long, T> {

    @Override
    default Class<Long> getSourceType() {
        return Long.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), LongConvert.class, 0);
    }
}
