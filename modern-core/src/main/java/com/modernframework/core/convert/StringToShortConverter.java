package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToShortConverter implements StringConverter<Short> {

    @Override
    public Short convert(String source) {
        return StringUtils.isNotEmpty(source) ?
                Short.valueOf(source) : null;
    }


    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 2;
    }
}
