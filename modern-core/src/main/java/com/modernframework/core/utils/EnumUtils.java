package com.modernframework.core.utils;

import com.modernframework.core.func.Predicates;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 枚举工具类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class EnumUtils {

    /**
     * 判断某个值是存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param predicate 需要查找的条件
     * @return 是否存在
     */
    public static <E extends Enum<E>> boolean contains(final Class<E> enumClass, Predicate<E>... predicate) {
        return contains(enumClass, Predicates.and(predicate));
    }


    /**
     * 判断某个值是存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param predicate 需要查找的条件
     * @return 是否存在
     */
    public static <E extends Enum<E>> boolean contains(final Class<E> enumClass, Predicate<E> predicate) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(predicate)
                .count() > 0L;
    }

    /**
     * 判断某个值是存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param val       需要查找的值
     * @return 是否存在
     */
    public static <E extends Enum<E>> boolean contains(final Class<E> enumClass, String val) {
        return contains(enumClass, v -> v.name().equals(val));
    }


    /**
     * 判断某个值是不存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param val       需要查找的值
     * @return 是否不存在
     */
    public static <E extends Enum<E>> boolean notContains(final Class<E> enumClass, String val) {
        return !contains(enumClass, val);
    }

    /**
     * 通过 某字段对应值 获取 枚举，获取不到时为 {@code null}
     *
     * @param condition 条件字段
     * @param value     条件字段值
     * @param <E>       枚举类型
     * @param <C>       字段类型
     * @return 对应枚举 ，获取不到时为 {@code null}
     */
    public static <E extends Enum<E>, C> E getBy(Function<E, C> condition, C value) {
        Class<E> implClass = LambdaUtils.getRealClass(condition);
        if (Enum.class.equals(implClass)) {
            implClass = LambdaUtils.getRealClass(condition);
        }
        return Arrays.stream(implClass.getEnumConstants())
                .filter(e -> condition.callWithRuntimeException(e).equals(value))
                .findAny().orElse(null);
    }

}
