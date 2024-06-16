package com.modernframework.core.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class TypeUtilsTest {
    
    interface IA<T> {}

    class CA implements IA<Integer> {}
    
    abstract class B<T extends Map> implements IA<T> {}

    abstract class CB implements IA<TreeMap> {}
    
    
    
    @Test
    public void getFirstClassArgumentsRecursionTest() {
        Assert.assertEquals(Integer.class, TypeUtils.getFirstClassArgumentsRecursion(CA.class));
        Assert.assertEquals(TreeMap.class, TypeUtils.getFirstClassArgumentsRecursion(CB.class));
    }
    
}
