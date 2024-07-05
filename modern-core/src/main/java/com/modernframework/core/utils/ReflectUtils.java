package com.modernframework.core.utils;


import com.modernframework.core.convert.ConvertUtils;
import com.modernframework.core.func.Streams;
import com.modernframework.core.lang.Asserts;
import com.modernframework.core.map.WeakConcurrentMap;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * ReflectUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class ReflectUtils {

    /**
     * 构造对象缓存
     */
    private static final WeakConcurrentMap<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new WeakConcurrentMap<>();

    /**
     * 字段缓存
     */
    private static final WeakConcurrentMap<Class<?>, Field[]> FIELDS_CACHE = new WeakConcurrentMap<>();
    /**
     * 方法缓存
     */
    private static final WeakConcurrentMap<Class<?>, Method[]> METHODS_CACHE = new WeakConcurrentMap<>();

    /**
     * 获得一个类中所有字段列表，包括其父类中的字段<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFields(Class<?> beanClass) throws SecurityException {
        Asserts.notNull(beanClass);
        return FIELDS_CACHE.computeIfAbsent(beanClass, () -> getFieldsDirectly(beanClass, true));
    }

    /**
     * 获得一个类中所有满足条件的字段列表，包括其父类中的字段<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     *
     * @param beanClass    类
     * @param fieldFilters field过滤器，过滤掉不需要的field
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    @SafeVarargs
    public static Field[] getFields(Class<?> beanClass, Predicate<Field>... fieldFilters) throws SecurityException {
        return Streams.filter(getFields(beanClass), fieldFilters).toArray(new Field[0]);
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     *
     * @param beanClass            类
     * @param withSuperClassFields 是否包括父类的字段列表
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFields) throws SecurityException {
        Asserts.notNull(beanClass);

        List<Field> allFields = new LinkedList<>();
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (ArrayUtils.isEmpty(allFields)) {
                allFields = Arrays.stream(declaredFields).collect(Collectors.toList());
            } else {
                allFields.addAll(Arrays.stream(declaredFields).collect(Collectors.toList()));
            }
            searchType = withSuperClassFields ? searchType.getSuperclass() : null;
        }
        return allFields.stream().toArray(Field[]::new);
    }

    /**
     * 获得一个类中所有方法列表，直接反射获取，无缓存<br>
     * 接口获取方法和默认方法，获取的方法包括：
     * <ul>
     *     <li>本类中的所有方法（包括static方法）</li>
     *     <li>父类中的所有方法（包括static方法）</li>
     *     <li>Object中（包括static方法）</li>
     * </ul>
     *
     * @param beanClass            类或接口
     * @param withSupers           是否包括父类或接口的方法列表
     * @param withMethodFromObject 是否包括Object中的方法
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getMethodsDirectly(Class<?> beanClass, boolean withSupers, boolean withMethodFromObject) throws SecurityException {
        Asserts.notNull(beanClass);
        if (beanClass.isInterface()) {
            // 对于接口，直接调用Class.getMethods方法获取所有方法，因为接口都是public方法
            return withSupers ? beanClass.getMethods() : beanClass.getDeclaredMethods();
        }
        Map<String, Method> methodMap = new LinkedHashMap<>();
        Class<?> searchType = beanClass;
        // 顺序是从底部实现往上方接口查找，以底部实现的方法为主
        while (searchType != null) {
            if (!withMethodFromObject && Object.class == searchType) {
                break;
            }

            final List<Method> methods = Arrays.asList(searchType.getDeclaredMethods());
            if (CollectionUtils.isNotEmpty(methods)) {
                methods.forEach(method -> {
                    String uniqueKey = getMethodUniqueKey(method);
                    methodMap.putIfAbsent(uniqueKey, method);
                });
            }

            final List<Method> defaultMethodsFromInterface = getDefaultMethodsFromInterface(searchType);
            if (CollectionUtils.isNotEmpty(defaultMethodsFromInterface)) {
                defaultMethodsFromInterface.forEach(method -> {
                    String uniqueKey = getMethodUniqueKey(method);
                    methodMap.putIfAbsent(uniqueKey, method);
                });
            }
            searchType = (withSupers && !searchType.isInterface()) ? searchType.getSuperclass() : null;
        }

        return methodMap.values().toArray(new Method[0]);
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param beanClass 类，非{@code null}
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     * @since 1.0.0
     */
    public static Method[] getMethods(Class<?> beanClass) throws SecurityException {
        Asserts.notNull(beanClass);
        return METHODS_CACHE.computeIfAbsent(beanClass,
                () -> getMethodsDirectly(beanClass, true, true));
    }

    /**
     * 获得指定类过滤后的方法列表
     *
     * @param clazz   查找方法的类
     * @param filters 过滤器
     * @return 过滤后的方法列表
     * @throws SecurityException 安全异常
     */
    @SafeVarargs
    public static Method[] getMethods(Class<?> clazz, Predicate<Method>... filters) throws SecurityException {
        if (null == clazz) {
            return null;
        }
        return Streams.filter(getMethods(clazz), filters).toArray(new Method[0]);
    }

    /**
     * 查找指定对象中的所有方法（包括非public方法），也包括父对象和Object类的方法
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。
     * </p>
     *
     * @param obj        被查找的对象，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param args       参数
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getMethodOfObj(Object obj, String methodName, Object... args) throws SecurityException {
        if (null == obj || StringUtils.isBlank(methodName)) {
            return null;
        }
        return getMethod(obj.getClass(), methodName, ClassUtils.getClasses(args));
    }

    /**
     * 查找指定方法 如果找不到对应的方法则返回{@code null}
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。
     * </p>
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        return getMethod(clazz, false, methodName, paramTypes);
    }

    /**
     * 查找指定方法 如果找不到对应的方法则返回{@code null}<br>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。<br>
     * 如果查找的方法有多个同参数类型重载，查找第一个找到的方法
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param ignoreCase 是否忽略大小写
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, boolean ignoreCase, String methodName, Class<?>... paramTypes) throws SecurityException {
        if (null == clazz || StringUtils.isBlank(methodName)) {
            return null;
        }

        Method res = null;
        final Method[] methods = getMethods(clazz);
        if (ArrayUtils.isNotEmpty(methods)) {
            for (Method method : methods) {
                if (StringUtils.equals(methodName, method.getName(), ignoreCase)
                        && ClassUtils.isAssignable(method.getParameterTypes(), paramTypes, false)
                        //排除协变桥接方法，pr#1965@Github
                        && (res == null
                        || res.getReturnType().isAssignableFrom(method.getReturnType()))) {
                    res = method;
                }
            }
        }
        return res;
    }

    /**
     * 获得一个类中所有构造列表
     *
     * @param <T>       构造的对象类型
     * @param beanClass 类，非{@code null}
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> beanClass) throws SecurityException {
        Asserts.notNull(beanClass);
        return (Constructor<T>[]) CONSTRUCTORS_CACHE.computeIfAbsent(beanClass, beanClass::getDeclaredConstructors);
    }

    /**
     * 查找类中的指定参数的构造方法，如果找到构造方法，会自动设置可访问为true
     *
     * @param <T>            对象类型
     * @param clazz          类
     * @param parameterTypes 参数类型，只要任何一个参数是指定参数的父类或接口或相等即可，此参数可以不传
     * @return 构造方法，如果未找到返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        if (null == clazz) {
            return null;
        }

        final Constructor<?>[] constructors = getConstructors(clazz);
        Class<?>[] pts;
        for (Constructor<?> constructor : constructors) {
            pts = constructor.getParameterTypes();
            if (ClassUtils.isAssignable(pts, parameterTypes, false)) {
                // 构造可访问
                constructor.setAccessible(true);
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    /**
     * 实例化对象
     *
     * @param <T>    对象类型
     * @param clazz  类
     * @param params 构造函数参数
     * @return 对象
     */
    public static <T> T newInstance(Class<T> clazz, Object... params) throws IllegalStateException {
        if (ArrayUtils.isEmpty(params)) {
            final Constructor<T> constructor = getConstructor(clazz);
            if (null == constructor) {
                throw new IllegalStateException(String.format("No constructor for [%s]", clazz));
            }
            try {
                return constructor.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(String.format("Instance class [%s] error!", clazz), e);
            }
        }

        final Class<?>[] paramTypes = ClassUtils.getClasses(params);
        final Constructor<T> constructor = getConstructor(clazz, paramTypes);
        if (null == constructor) {
            throw new IllegalStateException(String.format("No Constructor matched for parameter types: [%s]", new Object[]{paramTypes}));
        }
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Instance class [%s] error!", clazz), e);
        }
    }

    /**
     * 获取方法的唯一键，结构为:
     * <pre>
     *     返回类型#方法名:参数1类型,参数2类型...
     * </pre>
     *
     * @param method 方法
     * @return 方法唯一键
     * @since 1.0.0
     */
    private static String getMethodUniqueKey(Method method) {
        final StringBuilder sb = new StringBuilder();
        sb.append(method.getReturnType().getName()).append('#');
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }

    /**
     * 获取类对应接口中的非抽象方法（default方法）
     *
     * @param clazz 类
     * @return 方法列表
     */
    private static List<Method> getDefaultMethodsFromInterface(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method m : ifc.getMethods()) {
                if (m.getModifiers() != Modifier.ABSTRACT) {
                    result.add(m);
                }
            }
        }
        return result;
    }

    /**
     * 执行方法
     *
     * <p>
     * 对于用户传入参数会做必要检查，包括：
     *
     * <pre>
     *     1、忽略多余的参数
     *     2、参数不够补齐默认值
     *     3、传入参数为null，但是目标参数类型为原始类型，做转换
     * </pre>
     *
     * @param <T>    返回对象类型
     * @param obj    对象，如果执行静态方法，此值为{@code null}
     * @param method 方法（对象方法或static方法都可）
     * @param args   参数对象
     * @return 结果
     */
    public static <T> T invoke(Object obj, Method method, Object... args) {
        try {
            return invokeRaw(obj, method, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行方法
     *
     * <p>
     * 对于用户传入参数会做必要检查，包括：
     *
     * <pre>
     *     1、忽略多余的参数
     *     2、参数不够补齐默认值
     *     3、传入参数为null，但是目标参数类型为原始类型，做转换
     * </pre>
     *
     * @param <T>    返回对象类型
     * @param obj    对象，如果执行静态方法，此值为{@code null}
     * @param method 方法（对象方法或static方法都可）
     * @param args   参数对象
     * @return 结果
     * @throws InvocationTargetException 目标方法执行异常
     * @throws IllegalAccessException    访问异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeRaw(Object obj, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        ClassUtils.setAccessible(method);

        // 检查用户传入参数：
        // 1、忽略多余的参数
        // 2、参数不够补齐默认值
        // 3、通过NullWrapperBean传递的参数,会直接赋值null
        // 4、传入参数为null，但是目标参数类型为原始类型，做转换
        // 5、传入参数类型不对应，尝试转换类型
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] actualArgs = new Object[parameterTypes.length];
        if (null != args) {
            for (int i = 0; i < actualArgs.length; i++) {
                if (i >= args.length || null == args[i]) {
                    // 越界或者空值
                    actualArgs[i] = ClassUtils.getDefaultValue(parameterTypes[i]);
                } else if (args[i] instanceof ClassUtils.NullWrapperBean) {
                    //如果是通过NullWrapperBean传递的null参数,直接赋值null
                    actualArgs[i] = null;
                } else if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
                    //对于类型不同的字段，尝试转换，转换失败则使用原对象类型
                    final Object targetValue = ConvertUtils.convertIfPossible(args[i], parameterTypes[i]);
                    if (null != targetValue) {
                        actualArgs[i] = targetValue;
                    } else {
                        actualArgs[i] = args[i];
                    }
                } else {
                    actualArgs[i] = args[i];
                }
            }
        }

        if (method.isDefault()) {
            // 当方法是default方法时，尤其对象是代理对象，需使用句柄方式执行
            // 代理对象情况下调用method.invoke会导致循环引用执行，最终栈溢出
            return MethodHandleUtils.invokeSpecial(obj, method, args);
        }

        return (T) method.invoke(ClassUtils.isStatic(method) ? null : obj, actualArgs);
    }

    /**
     * 执行对象中指定方法
     * 如果需要传递的参数为null,请使用NullWrapperBean来传递,不然会丢失类型信息
     *
     * @param <T>        返回对象类型
     * @param obj        方法所在对象
     * @param methodName 方法名
     * @param args       参数列表
     * @return 执行结果
     */
    public static <T> T invoke(Object obj, String methodName, Object... args) {
        Asserts.notNull(obj, "Object to get method must be not null!");
        Asserts.notBlank(methodName, "Method name must be not blank!");

        final Method method = getMethodOfObj(obj, methodName, args);
        if (null == method) {
            throw new RuntimeException(String.format("No such method: [%s] from [%s]",
                    methodName, obj.getClass()));
        }
        return invoke(obj, method, args);
    }


}
