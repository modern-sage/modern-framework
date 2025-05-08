/**
 * module
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
module modern.core {
    requires cglib;
    requires java.annotation;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires java.xml;
    requires org.slf4j;

    uses com.modernframework.core.convert.Converter;
    provides com.modernframework.core.convert.Converter with
            com.modernframework.core.convert.StringToBigDecimalConverter, com.modernframework.core.convert.StringToBooleanConverter,
            com.modernframework.core.convert.StringToByteConverter, com.modernframework.core.convert.StringToCharacterConverter,
            com.modernframework.core.convert.StringToCharArrayConverter, com.modernframework.core.convert.StringToClassConverter,
            com.modernframework.core.convert.StringToDoubleConverter, com.modernframework.core.convert.StringToFloatConverter,
            com.modernframework.core.convert.StringToIntegerConverter, com.modernframework.core.convert.StringToLongConverter,
            com.modernframework.core.convert.StringToOptionalConverter, com.modernframework.core.convert.StringToShortConverter,
            com.modernframework.core.convert.StringToStringConverter, com.modernframework.core.convert.StringToURIConverter,
            com.modernframework.core.convert.StringToDateConverter, com.modernframework.core.convert.StringToLocalDateTimeConverter,
            com.modernframework.core.convert.byteconvert.ByteToBoolean, com.modernframework.core.convert.byteconvert.ByteToInteger,
            com.modernframework.core.convert.shortconvert.ShortToBigDecimal, com.modernframework.core.convert.shortconvert.ShortToInteger,
            com.modernframework.core.convert.shortconvert.ShortToLocalDateTime, com.modernframework.core.convert.shortconvert.ShortToLong,
            com.modernframework.core.convert.shortconvert.ShortToString,
            com.modernframework.core.convert.integerconvert.IntegerToBigDecimal,
            com.modernframework.core.convert.integerconvert.IntegerToByte, com.modernframework.core.convert.integerconvert.IntegerToBoolean,
            com.modernframework.core.convert.integerconvert.IntegerToLocalDateTime, com.modernframework.core.convert.integerconvert.IntegerToLong,
            com.modernframework.core.convert.integerconvert.IntegerToShort, com.modernframework.core.convert.integerconvert.IntegerToString,
            com.modernframework.core.convert.longconvert.LongToBigDecimal, com.modernframework.core.convert.longconvert.LongToInteger,
            com.modernframework.core.convert.longconvert.LongToLocalDateTime, com.modernframework.core.convert.longconvert.LongToShort,
            com.modernframework.core.convert.longconvert.LongToString,
            com.modernframework.core.convert.bigdecimalconvert.BigDecimalToInteger, com.modernframework.core.convert.bigdecimalconvert.BigDecimalToLocalDateTime,
            com.modernframework.core.convert.bigdecimalconvert.BigDecimalToLong, com.modernframework.core.convert.bigdecimalconvert.BigDecimalToShort,
            com.modernframework.core.convert.bigdecimalconvert.BigDecimalToString,
            com.modernframework.core.convert.localdatetimeconvert.LocalDateTimeToInteger, com.modernframework.core.convert.localdatetimeconvert.LocalDateTimeToString,
            com.modernframework.core.convert.localdatetimeconvert.LocalDateTimeToBigDecimal, com.modernframework.core.convert.localdatetimeconvert.LocalDateTimeToLong,
            com.modernframework.core.convert.localdatetimeconvert.LocalDateTimeToShort,
            com.modernframework.core.convert.timestampconvert.TimestampToLocalDateTime,
            com.modernframework.core.convert.booleanconvert.BooleanToByte,
            com.modernframework.core.convert.booleanconvert.BooleanToString,
            com.modernframework.core.convert.listconvert.ListToStringConverter
            ;

    exports com.modernframework.core.anno;
    exports com.modernframework.core.constant;
    exports com.modernframework.core.convert;
    exports com.modernframework.core.convert.mapconvert;
    exports com.modernframework.core.convert.listconvert;
    exports com.modernframework.core.convert.longconvert;
    exports com.modernframework.core.func;
    exports com.modernframework.core.lang;
    exports com.modernframework.core.map;
    exports com.modernframework.core.utils;
}
