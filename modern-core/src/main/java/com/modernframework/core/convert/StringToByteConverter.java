package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

import java.math.BigDecimal;

public class StringToByteConverter implements StringConverter<Byte> {

    @Override
    public Byte convert(String source) {
        return StringUtils.isNotEmpty(source) ? Byte.valueOf(source) : null;
    }

}
