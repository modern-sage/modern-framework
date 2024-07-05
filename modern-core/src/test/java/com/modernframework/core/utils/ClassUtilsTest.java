package com.modernframework.core.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * ClassUtilsTest
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class ClassUtilsTest {

    interface IA {}
    class SuperA implements IA {}
    interface IB {}
    class SuperAB extends SuperA implements IB {}
    class B extends  SuperAB{}

    @Test
    public void getMethodNameTest() {
        Set<Class<?>> allClasses = ClassUtils.getAllClasses(B.class, true);
        Assert.assertEquals(4, allClasses.size());
        Assert.assertTrue(allClasses.contains(B.class));
        Assert.assertTrue(allClasses.contains(SuperAB.class));
        Assert.assertTrue(allClasses.contains(SuperA.class));
        Assert.assertTrue(allClasses.contains(Object.class));

        Set<Class<?>> allSuperClasses = ClassUtils.getAllSuperClasses(B.class);
        Assert.assertEquals(3, allSuperClasses.size());
        Assert.assertTrue(allSuperClasses.contains(SuperAB.class));
        Assert.assertTrue(allSuperClasses.contains(SuperA.class));
        Assert.assertTrue(allSuperClasses.contains(Object.class));

        Set<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(B.class);
        Assert.assertEquals(2, allInterfaces.size());
        Assert.assertTrue(allInterfaces.contains(IB.class));
        Assert.assertTrue(allInterfaces.contains(IA.class));
    }

}
