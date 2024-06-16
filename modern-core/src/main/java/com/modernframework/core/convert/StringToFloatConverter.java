package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToFloatConverter implements StringConverter<Float> {

    @Override
    public Float convert(String source) {
        return StringUtils.isNotEmpty(source) ? Float.valueOf(source) : null;
    }

    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 4;
    }
}
