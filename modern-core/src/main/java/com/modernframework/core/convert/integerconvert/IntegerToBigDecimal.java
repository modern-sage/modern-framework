package com.modernframework.core.convert.integerconvert;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class IntegerToBigDecimal implements IntegerConverter<BigDecimal>{

    @Override
    public BigDecimal convert(Integer source) {
        return source != null ? BigDecimal.valueOf(source) : null;
    }
}
