package com.modernframework.core.convert.localdatetimeconvert;

import java.time.LocalDateTime;
/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LocalDateTimeToString implements LocalDateTimeConvert<String>{

    @Override
    public String convert(LocalDateTime source) {
        return source != null ? source.toString() : null;
    }
}
