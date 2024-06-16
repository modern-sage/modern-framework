package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToCharArrayConverter implements StringConverter<char[]> {

    @Override
    public char[] convert(String source) {
        return StringUtils.isNotEmpty(source) ? source.toCharArray() : null;
    }


    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 7;
    }
}
