package com.modernframework.core.convert.integerconvert;


import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
@FunctionalInterface
public interface IntegerConverter<T> extends Converter<Integer, T> {

    @Override
    default Class<Integer> getSourceType() {
        return Integer.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), IntegerConverter.class, 0);
    }
}
