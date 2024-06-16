package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToLongConverter implements StringConverter<Long> {

    @Override
    public Long convert(String source) {
        return StringUtils.isNotEmpty(source) ? Long.valueOf(source) : null;
    }


    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 1;
    }
}
