package com.modernframework.core.convert.bigdecimalconvert;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 14:18
 * @since 1.0.0
 */
public class BigDecimalToLong implements BigDecimalConvert<Long> {

    @Override
    public Long convert(BigDecimal source) {
        return source != null ? source.longValue() : null;
    }
}
