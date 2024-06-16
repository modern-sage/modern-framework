package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToDoubleConverter implements StringConverter<Double> {

    @Override
    public Double convert(String source) {
        return StringUtils.isNotEmpty(source) ? Double.valueOf(source) : null;
    }


    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 3;
    }
}
