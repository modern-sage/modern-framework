package com.modernframework.core.convert;

import com.modernframework.core.utils.NumberUtils;
import com.modernframework.core.utils.StringUtils;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * The class to convert {@link String} to {@link URI}
 *
 * @since 1.0.0
 */
public class StringToLocalDateTimeConverter implements StringConverter<LocalDateTime> {

    /**
     * 定义备选的日期时间格式
     */
   private final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    };

    @Override
    public LocalDateTime convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        // 如果是 Long 类型，尝试使用其他的转换器
        if(NumberUtils.isLong(source)) {
            return ConvertUtils.convert(Long.parseLong(source), LocalDateTime.class);
        }

        // 尝试使用备选格式进行转换
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(source, formatter);
            } catch (DateTimeParseException ignore) {
                // NOP
            }
        }

       return LocalDateTime.parse(source);
    }
}
