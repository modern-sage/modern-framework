package com.modernframework.core.convert.bigdecimalconvert;


import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 14:05
 * @since 1.0.0
 */
@FunctionalInterface
public interface BigDecimalConvert<T> extends Converter<BigDecimal, T> {

    @Override
    default Class<BigDecimal> getSourceType() {
        return BigDecimal.class;
    }


    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), BigDecimalConvert.class, 0);
    }
}
