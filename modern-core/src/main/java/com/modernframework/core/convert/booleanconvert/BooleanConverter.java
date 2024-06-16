package com.modernframework.core.convert.booleanconvert;


import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

/**
 * A class to covert {@link Boolean} to the target-typed value
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@FunctionalInterface
public interface BooleanConverter<T> extends Converter<Boolean, T> {

    @Override
    default Class<Boolean> getSourceType() {
        return Boolean.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), BooleanConverter.class, 0);
    }

}
