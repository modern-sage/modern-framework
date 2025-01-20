package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

import java.math.BigDecimal;

public class StringToBigDecimalConverter implements StringConverter<BigDecimal> {

    @Override
    public BigDecimal convert(String source) {
        return StringUtils.isNotEmpty(source) ? new BigDecimal(source) : null;
    }

}
