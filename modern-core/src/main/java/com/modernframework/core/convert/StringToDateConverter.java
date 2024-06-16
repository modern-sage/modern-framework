package com.modernframework.core.convert;

import com.modernframework.core.utils.DateUtils;
import com.modernframework.core.utils.NumberUtils;
import com.modernframework.core.utils.StringUtils;

import java.util.Date;

public class StringToDateConverter implements StringConverter<Date> {

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        if (NumberUtils.isLong(source)) {
            return DateUtils.parse(Long.valueOf(source));
        } else {
            return DateUtils.parse(source);
        }
    }
}
