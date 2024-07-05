package com.modernframework.core.utils;

import com.modernframework.core.lang.Asserts;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bean工具类
 * 把一个拥有对属性进行set和get方法的类，我们就可以称之为JavaBean。
 */
public abstract class BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

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
            Map<String, Class<?>> propertyMap = Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(x -> !"class".equals(x.getName()))
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
                beanMap.putAll(addProperties);
            }

            return target;
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }


    public static void converterBean(Object fromBean, Object toBean, String[] excludeProperties)
            throws InvocationTargetException, IllegalAccessException {
        List<String> excludes = Arrays.stream(excludeProperties).map(String::toLowerCase).collect(Collectors.toList());

        Method[] methods = fromBean.getClass().getMethods();
        for (Method method : methods) {

            String methodName = method.getName();
            if (!methodName.startsWith("get") || "getClass".equals(methodName)
                    || excludes.contains(methodName.replaceFirst("get", "").toLowerCase())) {
                continue;
            }
            Class<?> returnType = method.getReturnType();
            Object value = method.invoke(fromBean, new Object[]{});
            String setMethodName = String.format("set%s", methodName.replaceFirst("get", ""));
            try {
                Method setMethod = toBean.getClass().getMethod(setMethodName, returnType);
                setMethod.invoke(toBean, value);
            } catch (NoSuchMethodException e) {
            }
        }
    }

    public static void converterBean(Object fromBean, Object toBean)
            throws InvocationTargetException, IllegalAccessException {
        converterBean(fromBean, toBean, new String[]{});
    }

    public static <T> T converterBean(Object fromBean, Class<T> toBeanClass)
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
        T toBean = toBeanClass.newInstance();
        converterBean(fromBean, toBean);
        return toBean;
    }

    public static <T> List<T> converterBeans(List<?> fromBean, Class<T> toBeanClass) {
        List<T> target = new ArrayList<>();
        fromBean.forEach(x -> {
            try {
                target.add(converterBean(x, toBeanClass));
            } catch (Throwable e) {
                log.error("convert error", e);
            }
        });
        return target;
    }

}
