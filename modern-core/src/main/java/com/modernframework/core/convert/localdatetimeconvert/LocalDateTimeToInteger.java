package com.modernframework.core.convert.localdatetimeconvert;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LocalDateTimeToInteger implements LocalDateTimeConvert<Integer>{

    @Override
    public Integer convert(LocalDateTime source) {
        return source != null ? (int) Timestamp.valueOf(source).getTime() : null;
    }
}
