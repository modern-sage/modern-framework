package com.modernframework.core.utils;

import com.modernframework.core.lang.Asserts;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Bean工具类
 * 把一个拥有对属性进行set和get方法的类，我们就可以称之为JavaBean。
 */
public abstract class BeanUtils {

    /**
     * 获取Getter或Setter方法名对应的字段名称，规则如下：
     * <ul>
     *     <li>getXxxx获取为xxxx，如getName得到name。</li>
     *     <li>setXxxx获取为xxxx，如setName得到name。</li>
     *     <li>isXxxx获取为xxxx，如isName得到name。</li>
     *     <li>其它不满足规则的方法名抛出{@link IllegalArgumentException}</li>
     * </ul>
     *
     * @param getterOrSetterName Getter或Setter方法名
     * @return 字段名称
     * @throws IllegalArgumentException 非Getter或Setter方法
     */
    public static String getFieldName(String getterOrSetterName) {
        if (getterOrSetterName.startsWith("get") || getterOrSetterName.startsWith("set")) {
            return StringUtils.removePreAndLowerFirst(getterOrSetterName, 3);
        } else if (getterOrSetterName.startsWith("is")) {
            return StringUtils.removePreAndLowerFirst(getterOrSetterName, 2);
        } else {
            throw new IllegalArgumentException("Invalid Getter or Setter name: " + getterOrSetterName);
        }
    }

//    /**
//     * 复制Bean对象属性<br>
//     *
//     * @param source 源Bean对象
//     * @param target 目标Bean对象
//     */
//    public static Object copyProperties(Object source, Object target) {
//        return BeanCopierFactory.getBeanCopier(source, target).copy();
//    }
//
//    /**
//     * 复制Bean对象属性<br>
//     *
//     * @param source   源Bean对象
//     * @param supplier 目标Bean对象构造函数
//     */
//    public static <T> T copyProperties(Object source, Supplier<T> supplier) {
//        return BeanCopierFactory.getBeanCopier(source, supplier).copy();
//    }

    public static <T> T addExtra(T source, String key, Object value) {
        Asserts.notNull(key);
        Asserts.notNull(value);
        return addExtra(source, Map.of(key, value));
    }

    public static <T> T addExtra(T source, Map<String, Object> addProperties) {
        try {
            Class<?> sourceClass = source.getClass();
            // 创建生成类
            BeanGenerator generator = new BeanGenerator();
            // 设置父类
            generator.setSuperclass(sourceClass);
            // 设置属性
            // 内省获取源数据的内部结构
            BeanInfo beanInfo = Introspector.getBeanInfo(sourceClass);
            // 源数据的所有的字段信息
            Map<String, Class> propertyMap = Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(x -> false == "class".equals(x.getName()))
                    .collect(Collectors.toMap(PropertyDescriptor::getName, PropertyDescriptor::getPropertyType));

            // 添加字段属性
            if (CollectionUtils.isNotEmpty(addProperties)) {
                addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
            }
            BeanGenerator.addProperties(generator, propertyMap);
            // 动态生成实例
            T target = (T) generator.create();

            // 拷贝数据
            BeanCopier beanCopier = BeanCopier.create(sourceClass, target.getClass(), false);
            beanCopier.copy(source, target, null);

            // 利用 BeanMap 插入数据
            BeanMap beanMap = BeanMap.create(target);

            // 根据字典执行插入
            if (CollectionUtils.isNotEmpty(addProperties)) {
                addProperties.forEach((k, v) -> {
                    beanMap.put(k, v);
                });
            }

            return target;
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

}
