package com.modernframework.core.convert;


import com.modernframework.core.utils.ClassUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ConvertTest
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class ConvertTest {

    @Test
    public void convert() {
        Integer num = ConvertUtils.convertIfPossible("30000", Integer.class);
        Assert.assertNotNull(num);
        Assert.assertEquals(30000, (int) num);
    }

    @Test
    public void testShortConverters() {
        Short aa = 120;
        Assert.assertEquals(new BigDecimal("120"), ConvertUtils.convert(aa, BigDecimal.class));
        Assert.assertEquals(Integer.valueOf("120"), ConvertUtils.convert(aa, Integer.class));
        Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(120), ZoneId.systemDefault()),
                ConvertUtils.convert(aa, LocalDateTime.class));
        Assert.assertEquals(Long.valueOf("120"), ConvertUtils.convert(aa, Long.class));
        Assert.assertEquals("120", ConvertUtils.convert(aa, String.class));

    }


    @Test
    public void testIntegerConverter() {
        Integer aa = 120;
        Assert.assertEquals(Short.valueOf("120"), ConvertUtils.convert(aa, Short.class));
        Assert.assertEquals(new BigDecimal(aa), ConvertUtils.convert(aa, BigDecimal.class));
        Assert.assertEquals(Long.valueOf(aa), ConvertUtils.convert(aa, Long.class));
        Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(120), ZoneId.systemDefault()),
                ConvertUtils.convert(aa, LocalDateTime.class));
        Assert.assertEquals(String.valueOf(aa), ConvertUtils.convert(aa, String.class));
    }


    @Test
    public void testLongConvert() {
        Long aa = 120L;
        Assert.assertEquals(Integer.valueOf("120"), ConvertUtils.convert(aa, Integer.class));
        Assert.assertEquals(Short.valueOf("120"), ConvertUtils.convert(aa, Short.class));
        Assert.assertEquals(new BigDecimal(aa), ConvertUtils.convert(aa, BigDecimal.class));
        Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(120), ZoneId.systemDefault()),
                ConvertUtils.convert(aa, LocalDateTime.class));
        Assert.assertEquals(String.valueOf(aa), ConvertUtils.convert(aa, String.class));
    }

    @Test
    public void testBigDecimalConvert() {
        BigDecimal aa = new BigDecimal("120");
        Assert.assertEquals(Integer.valueOf("120"), ConvertUtils.convert(aa, Integer.class));
        Assert.assertEquals(Short.valueOf("120"), ConvertUtils.convert(aa, Short.class));
        Assert.assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(120), ZoneId.systemDefault()),
                ConvertUtils.convert(aa, LocalDateTime.class));
        Assert.assertEquals(Long.valueOf(120), ConvertUtils.convert(aa, Long.class));
        Assert.assertEquals(String.valueOf(120), ConvertUtils.convert(aa, String.class));
    }



    @Test
    public void testLocalDateConvert() {
        // 2023-12-13 14:47:24 的时间戳是：1702450044000
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 13, 14, 47, 24);
        Assert.assertEquals(Long.valueOf("1702450044000"), ConvertUtils.convert(localDateTime, Long.class));
        Assert.assertEquals(new BigDecimal("1702450044000"), ConvertUtils.convert(localDateTime, BigDecimal.class));
        Assert.assertEquals(localDateTime.toString(), ConvertUtils.convert(localDateTime, String.class));
        // 1702450044000的二进制： 11000110001100001111011100001110001100000 -> 转成short： 0001 1100 0110 0000 （7264）
        Assert.assertEquals(Short.valueOf("7264"), ConvertUtils.convert(localDateTime, Short.class));
        // 1702450044000的二进制： 11000110001100001111011100001110001100000 -> 转成Integer： 0110 0001 1110 1110 0001 1100 0110 0000 （1642994784）
        Assert.assertEquals(Integer.valueOf("1642994784"), ConvertUtils.convert(localDateTime, Integer.class));

    }

    @Test
    public void testStringConvert() {
        Assert.assertEquals(Boolean.TRUE, ConvertUtils.convert("true", Boolean.class));
        Assert.assertEquals(Boolean.TRUE, ConvertUtils.convert("1", Boolean.class));
        Assert.assertEquals(Boolean.FALSE, ConvertUtils.convert("0", Boolean.class));
    }

    @Test
    public void testBooleanConvert() {
        Field[] declaredFields = Demo.class.getDeclaredFields();
        Field declaredField = declaredFields[0];
        Class type = declaredField.getType();
        System.out.println(type);
        Class aClass1 = ClassUtils.primitiveToWrapper(type);
        System.out.println(aClass1);
        Assert.assertEquals(Boolean.TRUE, ConvertUtils.convert(true, type));
    }

    class Demo {
        boolean a;
    }

    @Test
    public void testListConverter() {
        List<Object> list = new ArrayList<>();
        Assert.assertEquals("", ConvertUtils.convert(list, String.class));
        list.add("a");
        list.add(2);
        list.add(true);
        list.add(false);
        Assert.assertEquals("[a,2,true,false]", ConvertUtils.convert(list, String.class));
    }

}
