package com.modernframework.core.utils;

import com.modernframework.core.func.Streams;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public abstract class TypeUtils {

    public static final Predicate<Class<?>> NON_OBJECT_TYPE_FILTER = t -> !Objects.equals(Object.class, t);

    public static boolean isParameterizedType(Type type) {
        return type instanceof ParameterizedType;
    }

    public static boolean isClass(Type type) {
        return type instanceof Class;
    }

    public static boolean hasTypeVariable(Type... types) {
        for (Type type : types) {
            if (type instanceof TypeVariable) {
                return true;
            }
        }
        return false;
    }

    public static Type getRawType(Type type) {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getRawType();
        } else {
            return type;
        }
    }

    public static Class<?> getRawClass(Type type) {
        Type rawType = getRawType(type);
        if (rawType instanceof Class) {
            return (Class<?>) rawType;
        }
        return null;
    }

    public static Class<?> getClass(Type type) {
        if (null != type) {
            if (type instanceof Class) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                return getRawClass(type);
            } else if (type instanceof TypeVariable) {
                return (Class<?>) ((TypeVariable<?>) type).getBounds()[0];
            } else if (type instanceof WildcardType) {
                final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        }
        return null;
    }

    /**
     * 在 sourceClass 找出第一个满足条件的泛型(Class)
     *
     * @param sourceClass 源类
     * @param predicate   判断函数
     */
    public static Class<?> findFirstActualTypeArgument(Class<?> sourceClass, Predicate<Class<?>> predicate) {
        if (sourceClass == null) {
            return null;
        }
        // genericTypes: 泛型类型数据
        // 加入 sourceClass 的接口
        List<Type> genericTypes = new LinkedList<>(Arrays.asList(sourceClass.getGenericInterfaces()));
        // 加入 sourceClass 的父类
        genericTypes.add(sourceClass.getGenericSuperclass());

        Class<?> firstActualTypeArgument = genericTypes.stream()
                .filter(type -> type instanceof ParameterizedType)
                .map(ParameterizedType.class::cast)
                .flatMap(p -> Arrays.stream(p.getActualTypeArguments()))
                .filter(type -> type instanceof Class)
                .map(Class.class::cast)
                .filter(predicate::test)
                .findFirst()
                .orElse(null);

        // 如果没有找到，进行递归查询
        if (firstActualTypeArgument == null) {
            // 从 genericClasses 找到是 Class 的元素 
            List<Class> genericClasses = genericTypes.stream()
                    .filter(type -> type instanceof Class)
                    .map(Class.class::cast)
                    .collect(Collectors.toList());

            for (Class<?> genericClass : genericClasses) {
                firstActualTypeArgument = findFirstActualTypeArgument(genericClass, predicate);
                // 当找到满足条件的，立即返回
                if (firstActualTypeArgument != null) {
                    return firstActualTypeArgument;
                }
            }
        }

        return firstActualTypeArgument;
    }


    /**
     * 递归获得指定类型中所有泛型参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到String
     *
     * @param targetClass 指定类型
     * @return 所有泛型参数类型
     */
    public static Class<?> getFirstClassArgumentsRecursion(Class<?> targetClass) {
        List<Class<?>> classList = getClassArgumentsRecursion(targetClass);
        if (CollectionUtils.isEmpty(classList)) {
            return null;
        } else {
            return classList.get(0);
        }
    }

    /**
     * 递归获得指定类型中所有泛型参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到String
     *
     * @param targetClass 指定类型
     * @return 所有泛型参数类型
     */
    public static List<Class<?>> getClassArgumentsRecursion(Class<?> targetClass) {
        List<Class<?>> classArguments = Collections.emptyList();
        if (null == targetClass) {
            // 终止条件
            return classArguments;
        }

        // 从接口查找
        for (Type superInterface : targetClass.getGenericInterfaces()) {
            if (superInterface instanceof ParameterizedType) {
                classArguments = getClassArguments((ParameterizedType) superInterface);
            }
            if (CollectionUtils.isNotEmpty(classArguments)) {
                return classArguments;
            }
        }

        Type superType = targetClass.getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            classArguments = getClassArguments((ParameterizedType) superType);
            if (CollectionUtils.isNotEmpty(classArguments)) {
                return classArguments;
            }
        }
        return getClassArgumentsRecursion(targetClass.getSuperclass());
    }

    public static List<Class<?>> getClassArguments(ParameterizedType parameterizedType) {
        if (parameterizedType == null) {
            return Collections.emptyList();
        }
        List<Class<?>> classArguments = new LinkedList<>();
        if (parameterizedType.getRawType() instanceof Class) {
            for (Type argument : parameterizedType.getActualTypeArguments()) {
                Class<?> typeArgument = getClass(argument);
                if (typeArgument != null) {
                    classArguments.add(typeArgument);
                }
            }
        }
        return classArguments;
    }

    /**
     * 获得指定类型中所有泛型参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到String
     *
     * @param type 指定类型
     * @return 所有泛型参数类型
     */
    public static Type[] getTypeArguments(Type type) {
        if (null == type) {
            return null;
        }

        final ParameterizedType parameterizedType = toParameterizedType(type);
        return (null == parameterizedType) ? null : parameterizedType.getActualTypeArguments();
    }

    /**
     * 将{@link Type} 转换为{@link ParameterizedType}<br>
     * {@link ParameterizedType}用于获取当前类或父类中泛型参数化后的类型<br>
     * 一般用于获取泛型参数具体的参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到B{@link ParameterizedType}，从而获取到String
     *
     * @param type {@link Type}
     * @return {@link ParameterizedType}
     * @since 1.0.0
     */
    public static ParameterizedType toParameterizedType(Type type) {
        ParameterizedType result = null;
        if (type instanceof ParameterizedType) {
            result = (ParameterizedType) type;
        } else if (type instanceof Class) {
            final Class<?> clazz = (Class<?>) type;
            Type genericSuper = clazz.getGenericSuperclass();
            if (null == genericSuper || Object.class.equals(genericSuper)) {
                // 如果类没有父类，而是实现一些定义好的泛型接口，则取接口的Type
                final Type[] genericInterfaces = clazz.getGenericInterfaces();
                if (ArrayUtils.isNotEmpty(genericInterfaces)) {
                    // 默认取第一个实现接口的泛型Type
                    genericSuper = genericInterfaces[0];
                }
            }
            result = toParameterizedType(genericSuper);
        }
        return result;
    }

    /**
     * 将{@link Type} 转换为{@link ParameterizedType}<br>
     * {@link ParameterizedType}用于获取当前类或父类中泛型参数化后的类型<br>
     * 一般用于获取泛型参数具体的参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到B{@link ParameterizedType}，从而获取到String
     *
     * @param type {@link Type}
     * @return {@link ParameterizedType}
     */
    public static ParameterizedType toParameterizedType(Type type, Predicate<Type> predicate) {
        ParameterizedType result = null;
        if (type instanceof ParameterizedType) {
            result = (ParameterizedType) type;
        } else if (type instanceof Class) {
            final Class<?> clazz = (Class<?>) type;
            Type genericSuper = clazz.getGenericSuperclass();
            if (null == genericSuper || Object.class.equals(genericSuper)) {
                // 如果类没有父类，而是实现一些定义好的泛型接口，则取接口的Type
                final Type[] genericInterfaces = clazz.getGenericInterfaces();
                if (ArrayUtils.isNotEmpty(genericInterfaces)) {
                    // 默认取第一个实现接口的泛型Type
                    genericSuper = genericInterfaces[0];
                }
            }
            result = toParameterizedType(genericSuper);
        }
        return result;
    }

    /**
     * 获取字段对应的Type类型<br>
     * 方法优先获取GenericType，获取不到则获取Type
     *
     * @param field 字段
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getType(Field field) {
        if (null == field) {
            return null;
        }
        return field.getGenericType();
    }

    /**
     * 获取方法的参数类型<br>
     * 优先获取方法的GenericParameterTypes，如果获取不到，则获取ParameterTypes
     *
     * @param method 方法
     * @param index  第几个参数的索引，从0开始计数
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getParamType(Method method, int index) {
        Type[] types = getParamTypes(method);
        if (null != types && types.length > index) {
            return types[index];
        }
        return null;
    }

    /**
     * 获取方法的参数类型列表<br>
     * 优先获取方法的GenericParameterTypes，如果获取不到，则获取ParameterTypes
     *
     * @param method 方法
     * @return {@link Type}列表，可能为{@code null}
     * @see Method#getGenericParameterTypes()
     * @see Method#getParameterTypes()
     */
    public static Type[] getParamTypes(Method method) {
        return null == method ? null : method.getGenericParameterTypes();
    }

    /**
     * 获取方法的参数类
     *
     * @param method 方法
     * @param index  第几个参数的索引，从0开始计数
     * @return 参数类，可能为{@code null}
     */
    public static Class<?> getParamClass(Method method, int index) {
        Class<?>[] classes = getParamClasses(method);
        if (null != classes && classes.length > index) {
            return classes[index];
        }
        return null;
    }

    /**
     * 解析方法的参数类型列表<br>
     * 依赖jre\lib\rt.jar
     *
     * @param method t方法
     * @return 参数类型类列表
     * @see Method#getGenericParameterTypes
     * @see Method#getParameterTypes
     */
    public static Class<?>[] getParamClasses(Method method) {
        return null == method ? null : method.getParameterTypes();
    }

    // ----------------------------------------------------------------------------------- Return Type

    /**
     * 获取方法的返回值类型<br>
     * 获取方法的GenericReturnType
     *
     * @param method 方法
     * @return {@link Type}，可能为{@code null}
     * @see Method#getGenericReturnType()
     * @see Method#getReturnType()
     */
    public static Type getReturnType(Method method) {
        return null == method ? null : method.getGenericReturnType();
    }

    /**
     * 解析方法的返回类型类列表
     *
     * @param method 方法
     * @return 返回值类型的类
     * @see Method#getGenericReturnType
     * @see Method#getReturnType
     */
    public static Class<?> getReturnClass(Method method) {
        return null == method ? null : method.getReturnType();
    }

    /**
     * 找到一个类的{@link ParameterizedType}
     *
     */
    public static Set<ParameterizedType> findParameterizedType(Class<?> sourceClass) {
        if (sourceClass == null) {
            return Collections.emptySet();
        }
        // Add Generic Interface
        List<Type> genericTypes = new LinkedList<>(Arrays.asList(sourceClass.getGenericInterfaces()));
        // Add Generic Super Class
        genericTypes.add(sourceClass.getGenericSuperclass());

        Set<ParameterizedType> parameterizedTypes = genericTypes.stream()
                .filter(type -> type instanceof ParameterizedType)
                .map(ParameterizedType.class::cast)
                .collect(Collectors.toSet());

        // If not found, try to search super types recursively
        if (parameterizedTypes.isEmpty()) {
            genericTypes.stream()
                    .filter(type -> type instanceof Class)
                    .map(Class.class::cast)
                    .forEach(superClass -> parameterizedTypes.addAll(findParameterizedType(superClass)));
        }

        return Collections.unmodifiableSet(parameterizedTypes);
    }

    /**
     * 获取目标类对应下标的参数化类型（范型）
     *
     * @param type           当前类
     * @param interfaceClass 上限类型
     * @param index          参数化类型下标
     * @return 参数化类型的具体类
     */
    public static <T> Class<T> findActualTypeArgumentClass(Type type, Class<?> interfaceClass, int index) {
        return findActualTypeArgumentClass(type, interfaceClass, false, index);
    }

    /**
     * 获取目标类的的参数化类型列表
     *
     * @param type           当前类
     * @param interfaceClass 上限类型
     * @return 参数化类型的具体类
     */
    public static <T> Class<T> findActualTypeArgumentClass(Type type, Class<?> interfaceClass, boolean sortParentToSun, int index) {
        List<Class<?>> list = findActualTypeArgumentClasses(type, interfaceClass, sortParentToSun);
        if (index > list.size() - 1) {
            return null;
        } else {
            return (Class<T>) list.get(index);
        }
    }

    public static List<Class<?>> findActualTypeArgumentClasses(Type type, Class<?> interfaceClass, boolean sortParentToSun) {
        List<Type> actualTypeArguments = findActualTypeArguments(type, interfaceClass, sortParentToSun);
        List<Class<?>> actualTypeArgumentClasses = new LinkedList<>();
        for (Type actualTypeArgument : actualTypeArguments) {
            Class<?> rawClass = getRawClass(actualTypeArgument);
            if (rawClass != null) {
                actualTypeArgumentClasses.add(rawClass);
            }
        }
        return Collections.unmodifiableList(actualTypeArgumentClasses);
    }

    public static List<Type> findActualTypeArguments(Type type, Class<?> interfaceClass, boolean sortParentToSun) {
        List<Type> actualTypeArguments = new LinkedList<>();
        getAllGenericParameterizedTypes(type, sortParentToSun, t -> ClassUtils.isAssignableFrom(interfaceClass, getRawClass(t)))
                .forEach(parameterizedType -> {
                    Class<?> rawClass = getRawClass(parameterizedType);
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    actualTypeArguments.addAll(asList(typeArguments));
                    Class<?> superClass = rawClass.getSuperclass();
                    if (superClass != null) {
                        actualTypeArguments.addAll(findActualTypeArguments(superClass, interfaceClass));
                    }
                });
        return unmodifiableList(actualTypeArguments);
    }

    public static List<Type> findActualTypeArguments(Type type, Class<?> interfaceClass) {
        return findActualTypeArguments(type, false, interfaceClass);
    }

    public static List<Type> findActualTypeArguments(Type type, boolean sortParentToSun, Class<?> interfaceClass) {
        List<Type> actualTypeArguments = new LinkedList<>();
        getAllGenericParameterizedTypes(type, sortParentToSun, t -> ClassUtils.isAssignableFrom(interfaceClass, getRawClass(t)))
                .forEach(parameterizedType -> {
                    Class<?> rawClass = getRawClass(parameterizedType);
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    actualTypeArguments.addAll(asList(typeArguments));
                    Class<?> superClass = rawClass.getSuperclass();
                    if (superClass != null) {
                        actualTypeArguments.addAll(findActualTypeArguments(superClass, interfaceClass));
                    }
                });

        return unmodifiableList(actualTypeArguments);
    }

    /**
     * Get all generic types(including super classes and interfaces) that are assignable from {@link ParameterizedType} interface
     *
     * @param type            the specified type
     * @param sortParentToSun 默认为false，排序为 子 -> 父，倒序为 父 -> 子
     * @param typeFilters     one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    @SafeVarargs
    public static List<ParameterizedType> getAllGenericParameterizedTypes(Type type, boolean sortParentToSun,
                                                                          Predicate<ParameterizedType>... typeFilters) {
        List<ParameterizedType> allGenericTypes = new LinkedList<>();
        allGenericTypes.addAll(getAllGenericParameterizedTypesFromSuperClasses(type, sortParentToSun, typeFilters));
        allGenericTypes.addAll(getAllGenericParameterizedTypesFromInterfaces(type, sortParentToSun, typeFilters));
        return unmodifiableList(allGenericTypes);
    }

    /**
     * Get all generic super classes that are assignable from {@link ParameterizedType} interface
     *
     * @param type            the specified type
     * @param sortParentToSun 默认为false，排序为 子 -> 父，倒序为 父 -> 子
     * @param typeFilters     one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    @SafeVarargs
    public static List<ParameterizedType> getAllGenericParameterizedTypesFromSuperClasses(Type type, boolean sortParentToSun,
                                                                                          Predicate<ParameterizedType>... typeFilters) {
        Class<?> rawClass = getRawClass(type);
        if (rawClass == null || rawClass.isInterface()) {
            return emptyList();
        }
        List<Class<?>> allTypes = new LinkedList<>();
        allTypes.add(rawClass);
        allTypes.addAll(ClassUtils.getAllSuperClasses(rawClass, NON_OBJECT_TYPE_FILTER));
        if (sortParentToSun) {
            Collections.reverse(allTypes);
        }
        List<ParameterizedType> allGenericSuperClasses = allTypes
                .stream()
                .map(Class::getGenericSuperclass)
                .filter(x -> x instanceof ParameterizedType)
                .map(ParameterizedType.class::cast)
                .collect(Collectors.toList());
        return Streams.filter(allGenericSuperClasses, typeFilters);
    }

    /**
     * Get all generic interfaces that are assignable from {@link ParameterizedType} interface <br/>
     * 顺序：[A, ParameterizedType, Type]
     *
     * @param type            the specified type
     * @param sortParentToSun 默认为false，排序为 子 -> 父，倒序为 父 -> 子
     * @param typeFilters     one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    @SafeVarargs
    public static List<ParameterizedType> getAllGenericParameterizedTypesFromInterfaces(Type type, boolean sortParentToSun,
                                                                                        Predicate<ParameterizedType>... typeFilters) {
        Class<?> rawClass = getRawClass(type);
        if (rawClass == null) {
            return emptyList();
        }
        List<Class<?>> allTypes = new LinkedList<>();
        allTypes.add(rawClass);
        allTypes.addAll(ClassUtils.getAllSuperClasses(rawClass, NON_OBJECT_TYPE_FILTER));
        allTypes.addAll(ClassUtils.getAllInterfaces(rawClass));
        if (sortParentToSun) {
            Collections.reverse(allTypes);
        }
        List<ParameterizedType> allGenericInterfaces = allTypes
                .stream()
                .map(Class::getGenericInterfaces)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .filter(x -> x instanceof ParameterizedType)
                .map(ParameterizedType.class::cast)
                .collect(toList());
        return Streams.filter(allGenericInterfaces, typeFilters);
    }



}
