package com.modernframework.core.convert.bigdecimalconvert;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 14:12
 * @since 1.0.0
 */
public class BigDecimalToShort implements BigDecimalConvert<Short>{

    @Override
    public Short convert(BigDecimal source) {
        return source != null ? source.shortValue() : null;
    }
}
