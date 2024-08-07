package com.modernframework.core.utils;

import com.modernframework.core.func.SerialFunction;
import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.MethodHandleInfo;

public class LambdaUtilsTest {

    @Test
    public void getMethodNameTest() {
        final String methodName = LambdaUtils.getMethodName(MyTeacher::getAge);
        Assert.assertEquals("getAge", methodName);
    }

    @Test
    public void getFieldNameTest() {
        final String fieldName = LambdaUtils.getFieldName(MyTeacher::getAge);
        Assert.assertEquals("age", fieldName);
    }

    @Test
    public void resolveTest() {
        // 引用构造函数
        Assert.assertEquals(MethodHandleInfo.REF_newInvokeSpecial,
                LambdaUtils.resolve(MyTeacher::new).getImplMethodKind());
        // 数组构造函数引用
        Assert.assertEquals(MethodHandleInfo.REF_invokeStatic,
                LambdaUtils.resolve(MyTeacher[]::new).getImplMethodKind());
        // 引用静态方法
        Assert.assertEquals(MethodHandleInfo.REF_invokeStatic,
                LambdaUtils.resolve(MyTeacher::takeAge).getImplMethodKind());
        // 引用特定对象的实例方法
        Assert.assertEquals(MethodHandleInfo.REF_invokeVirtual,
                LambdaUtils.resolve(new MyTeacher()::getAge).getImplMethodKind());
        // 引用特定类型的任意对象的实例方法
        Assert.assertEquals(MethodHandleInfo.REF_invokeVirtual,
                LambdaUtils.resolve(MyTeacher::getAge).getImplMethodKind());
    }


    @Test
    public void getRealClassTest() {
        // 引用特定类型的任意对象的实例方法
        final Class<MyTeacher> functionClass = LambdaUtils.getRealClass(MyTeacher::getAge);
        Assert.assertEquals(MyTeacher.class, functionClass);
        // 枚举测试，不会导致类型擦除
        final Class<LambdaKindEnum> enumFunctionClass = LambdaUtils.getRealClass(LambdaKindEnum::ordinal);
        Assert.assertEquals(LambdaKindEnum.class, enumFunctionClass);
        // 调用父类方法，能获取到正确的子类类型
        final Class<MyTeacher> superFunctionClass = LambdaUtils.getRealClass(MyTeacher::getId);
        Assert.assertEquals(MyTeacher.class, superFunctionClass);

        final MyTeacher myTeacher = new MyTeacher();
        // 引用特定对象的实例方法
        final Class<MyTeacher> supplierClass = LambdaUtils.getRealClass(myTeacher::getAge);
        Assert.assertEquals(MyTeacher.class, supplierClass);
        // 枚举测试，只能获取到枚举类型
        final Class<Enum<?>> enumSupplierClass = LambdaUtils.getRealClass(LambdaKindEnum.REF_NONE::ordinal);
        Assert.assertEquals(Enum.class, enumSupplierClass);
        // 调用父类方法，只能获取到父类类型
        final Class<Entity<?>> superSupplierClass = LambdaUtils.getRealClass(myTeacher::getId);
        Assert.assertEquals(Entity.class, superSupplierClass);

        // 引用静态带参方法，能够获取到正确的参数类型
        final Class<MyTeacher> staticFunctionClass = LambdaUtils.getRealClass(MyTeacher::takeAgeBy);
        Assert.assertEquals(MyTeacher.class, staticFunctionClass);
        // 引用父类静态带参方法，只能获取到父类类型
        final Class<Entity<?>> staticSuperFunctionClass = LambdaUtils.getRealClass(MyTeacher::takeId);
        Assert.assertEquals(Entity.class, staticSuperFunctionClass);

        // 引用静态无参方法，能够获取到正确的类型
        final Class<MyTeacher> staticSupplierClass = LambdaUtils.getRealClass(MyTeacher::takeAge);
        Assert.assertEquals(MyTeacher.class, staticSupplierClass);
        // 引用父类静态无参方法，能够获取到正确的参数类型
        final Class<MyTeacher> staticSuperSupplierClass = LambdaUtils.getRealClass(MyTeacher::takeIdBy);
        Assert.assertEquals(MyTeacher.class, staticSuperSupplierClass);
    }

    static class MyStudent {

        private String name;

        public MyStudent() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Entity<T> {

        private T id;

        public Entity() {
        }


        public static <T> T takeId() {
            return new Entity<T>().getId();
        }

        public static <T> T takeIdBy(final Entity<T> entity) {
            return entity.getId();
        }


        public T getId() {
            return id;
        }

        public void setId(T id) {
            this.id = id;
        }
    }

    static class MyTeacher extends Entity<MyTeacher> {

        private String age;
        public MyTeacher() {
        }

        public static String takeAge() {
            return new MyTeacher().getAge();
        }

        public static String takeAgeBy(final MyTeacher myTeacher) {
            return myTeacher.getAge();
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    /**
     * 测试Lambda类型枚举
     */
    enum LambdaKindEnum {
        REF_NONE,
        REF_getField,
        REF_getStatic,
        REF_putField,
        REF_putStatic,
        REF_invokeVirtual,
        REF_invokeStatic,
        REF_invokeSpecial,
        REF_newInvokeSpecial,
    }

    @Test
    public void lambdaClassNameTest() {
        final String lambdaClassName1 = LambdaUtilTestHelper.getLambdaClassName(MyTeacher::getAge);
        final String lambdaClassName2 = LambdaUtilTestHelper.getLambdaClassName(MyTeacher::getAge);
        Assert.assertNotEquals(lambdaClassName1, lambdaClassName2);
    }

    static class LambdaUtilTestHelper {
        public static <P> String getLambdaClassName(final SerialFunction<P, ?> func) {
            return func.getClass().getName();
        }
    }
}
