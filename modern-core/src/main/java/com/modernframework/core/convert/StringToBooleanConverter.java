package com.modernframework.core.convert;

import java.util.Arrays;

public class StringToBooleanConverter implements StringConverter<Boolean> {

    private final static String[] TRUE_STR_ARR = new String[]{"1", "true"};

    @Override
    public Boolean convert(String source) {
       return Arrays.stream(TRUE_STR_ARR).anyMatch(source::equalsIgnoreCase);
    }

    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 5;
    }
}
