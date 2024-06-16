package com.modernframework.core.convert.localdatetimeconvert;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LocalDateTimeToShort implements LocalDateTimeConvert<Short>{

    @Override
    public Short convert(LocalDateTime source) {
        return source != null ? (short) Timestamp.valueOf(source).getTime() : null;
    }
}
