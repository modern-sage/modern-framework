package com.modernframework.core.convert.bigdecimalconvert;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 14:21
 * @since 1.0.0
 */
public class BigDecimalToString implements BigDecimalConvert<String>{

    @Override
    public String convert(BigDecimal source) {
        return source != null ? source.toString() : null;
    }
}
