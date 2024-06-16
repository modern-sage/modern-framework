package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToIntegerConverter implements StringConverter<Integer> {

    @Override
    public Integer convert(String source) {
        return StringUtils.isNotEmpty(source) ? Integer.valueOf(source) : null;
    }

    @Override
    public int getPriority() {
        return NORMAL_PRIORITY;
    }
}
