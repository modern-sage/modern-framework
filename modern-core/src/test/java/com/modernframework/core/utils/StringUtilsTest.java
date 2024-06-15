package com.modernframework.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void subTest() {
        final String a = "abcderghigh";
        final String pre = StringUtils.sub(a, -5, a.length());
        Assert.assertEquals("ghigh", pre);
    }




}
