package com.modernframework.core.convert;


import com.modernframework.core.lang.Prioritized;
import com.modernframework.core.utils.ClassUtils;
import com.modernframework.core.utils.TypeUtils;

import java.util.function.Function;

/**
 * 转换器接口函数
 *
 * @param <S> The source type
 * @param <T> The target type
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Converter<S, T> extends Function<S, T>, Prioritized {

    default boolean accept(Class<?> sourceType, Class<?> targetType) {
        return ClassUtils.isAssignableFrom(getSourceType(), sourceType)
                && ClassUtils.isAssignableFrom(getTargetType(), targetType);
    }

    T convert(S source);

    @Override
    default T apply(S source) {
        return convert(source);
    }

    /**
     * Get the source type
     *
     * @return non-null
     */
    default Class<S> getSourceType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), Converter.class, true, 0);
    }

    /**
     * Get the target type
     *
     * @return non-null
     */
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), Converter.class, true, 1);
    }

}
