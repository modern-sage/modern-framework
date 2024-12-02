package com.modernframework.core.func;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.modernframework.core.utils.ArrayUtils;

import static com.modernframework.core.func.Predicates.and;
import static com.modernframework.core.func.Predicates.or;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Streams <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
public interface Streams {

    static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    static <T, I extends Iterable<T>> Stream<T> filterStream(I values, Predicate<? super T> predicate) {
        return stream(values).filter(predicate);
    }

    static <T, I extends Iterable<T>> Stream<T> filterStream(I values, Predicate<? super T>... predicates) {
        return ArrayUtils.isEmpty(predicates) ? stream(values) : filterStream(values, and(predicates));
    }

    static <E, L extends List<E>> List<E> filter(L values, Predicate<? super E> predicate) {
        final L result;
        if (predicate == null) {
            result = values;
        } else {
            result = (L) filterStream(values, predicate).collect(toList());
        }
        return unmodifiableList(result);
    }

    static <E, L extends List<E>> List<E> filter(L values, Predicate<? super E>... predicates) {
        return filter(values, and(predicates));
    }

    static <E, S extends Set<E>> Set<E> filter(S values, Predicate<? super E> predicate) {
        final S result;
        if (predicate == null) {
            result = values;
        } else {
            result = (S) filterStream(values, predicate).collect(toSet());
        }
        return unmodifiableSet(result);
    }

    static <E, S extends Set<E>> Set<E> filter(S values, Predicate<? super E>... predicates) {
        return filter(values, and(predicates));
    }

    static <E, Q extends Queue<E>> Queue<E> filter(Q values, Predicate<? super E>... predicates) {
        return filter(values, and(predicates));
    }

    static <T, S extends Iterable<T>> S filter(S values, Predicate<? super T> predicate) {
        if (values instanceof Set) {
            return (S) filter((Set) values, predicate);
        } else if (values instanceof List) {
            return (S) filter((List) values, predicate);
        } else if (values instanceof Queue) {
            return (S) filter((Queue) values, predicate);
        }
        String message = format("The 'values' type can't be supported!", values.getClass().getName());
        throw new UnsupportedOperationException(message);
    }

    static <T, S extends Iterable<T>> S filter(S values, Predicate<? super T>... predicates) {
        return filter(values, and(predicates));
    }

    /**
     * 过滤数组
     *
     * @param values
     * @param predicates
     * @param <T>
     * @return
     */
    static <T> List<T> filter(T[] values, Predicate<? super T>... predicates) {
        if (values == null || ArrayUtils.isEmpty(predicates)) {
            return Collections.emptyList();
        }
        return filter(Arrays.asList(values), predicates);
    }

    static <T, S extends Iterable<T>> S filterAny(S values, Predicate<? super T>... predicates) {
        return filter(values, or(predicates));
    }

    static <T> T filterFirst(Iterable<T> values, Predicate<? super T> predicate) {
        return stream(values)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    static <T> T filterFirst(Iterable<T> values, Predicate<? super T>... predicates) {
        return filterFirst(values, and(predicates));
    }

    static <T, R> List<R> map(List<T> values, Function<T, R> mapper) {
        return stream(values)
                .map(mapper)
                .collect(Collectors.toList());
    }

    static <T, R> Set<R> map(Set<T> values, Function<T, R> mapper) {
        return stream(values)
                .map(mapper)
                .collect(toSet());
    }
}


