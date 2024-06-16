package com.modernframework.core.convert.bigdecimalconvert;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 14:08
 * @since 1.0.0
 */
public class BigDecimalToInteger implements BigDecimalConvert<Integer>{

    @Override
    public Integer convert(BigDecimal source) {
        return source != null ? source.intValue() : null;
    }
}
