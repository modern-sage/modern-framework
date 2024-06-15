package com.modernframework.core.utils;

import java.util.*;

/**
 * 集合相关工具类，此工具方法针对{@link Collection}及其实现类封装的工具。
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class CollectionUtils {

    public static <T> ArrayList<T> newArrayList(Iterable<T> values) {
        ArrayList<T> list = new ArrayList<>();
        values.forEach(list::add);
        return list;
    }

    public static <T> ArrayList<T> newArrayList(T... values) {
        ArrayList<T> list = new ArrayList<>();
        Arrays.stream(values).forEach(list::add);
        return list;
    }

    public static <T> LinkedList<T> newLinkedList(T... values) {
        LinkedList<T> list = new LinkedList<>();
        Arrays.stream(values).forEach(list::add);
        return list;
    }

    // ---------------------------------------------------------------------- isEmpty

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Enumeration是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return 是否为空
     */
    public static boolean isEmpty(Enumeration<?> enumeration) {
        return null == enumeration || false == enumeration.hasMoreElements();
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     * @see MapUtils#isEmpty(Map)
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }


    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return iterable == null || !iterable.iterator().hasNext();
    }

    /**
     * Iterator是否为空
     *
     * @param iterator Iterator对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

// ---------------------------------------------------------------------- isNotEmpty

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return false == isEmpty(collection);
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * Iterator是否为空
     *
     * @param Iterator Iterator对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Iterator<?> Iterator) {
        return !isEmpty(Iterator);
    }

    /**
     * Enumeration是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return 是否为空
     */
    public static boolean isNotEmpty(Enumeration<?> enumeration) {
        return null != enumeration && enumeration.hasMoreElements();
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     * @see MapUtils#isNotEmpty(Map)
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtils.isNotEmpty(map);
    }

    /**
     * 去重集合
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return {@link ArrayList}
     */
    public static <T> ArrayList<T> distinct(Collection<T> collection) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        } else if (collection instanceof Set) {
            return new ArrayList<>(collection);
        } else {
            return new ArrayList<>(new LinkedHashSet<>(collection));
        }
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterator   要加入的{@link Iterator}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> iterator) {
        if (null != collection && null != iterator) {
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
        }
        return collection;
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterable   要加入的内容{@link Iterable}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
        if (iterable == null) {
            return collection;
        }
        return addAll(collection, iterable.iterator());
    }

    /**
     * 加入全部
     *
     * @param <T>         集合元素类型
     * @param collection  被加入的集合 {@link Collection}
     * @param enumeration 要加入的内容{@link Enumeration}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Enumeration<T> enumeration) {
        if (null != collection && null != enumeration) {
            while (enumeration.hasMoreElements()) {
                collection.add(enumeration.nextElement());
            }
        }
        return collection;
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param values     要加入的内容数组
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, T[] values) {
        if (null != collection && null != values) {
            Collections.addAll(collection, values);
        }
        return collection;
    }

    /**
     * 是否存在交集
     * @param collection1
     * @param collection2
     */
    public static boolean existIntersection(Collection<?> collection1, Collection<?> collection2) {
        try {
            Collection<?> tmp = new ArrayList<>(collection1);
            tmp.retainAll(collection2);
            return !tmp.isEmpty();
        } catch (Throwable e) {
            return false;
        }
    }



}
