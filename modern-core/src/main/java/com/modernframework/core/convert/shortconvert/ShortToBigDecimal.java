package com.modernframework.core.convert.shortconvert;

import java.math.BigDecimal;

public class ShortToBigDecimal implements ShortConverter<BigDecimal> {
    @Override
    public BigDecimal convert(Short source) {
        return source != null ? BigDecimal.valueOf(source) : null;
    }
}
