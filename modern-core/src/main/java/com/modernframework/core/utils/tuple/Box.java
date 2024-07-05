package com.modernframework.core.utils.tuple;

import java.util.Objects;

/**
 * Box
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class Box<T> {

    private T content;

    public Box() {
    }

    public Box(T t) {
        this.content = t;
    }

    public void set(T t) {
        this.content = t;
    }

    public T unwrap() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof @SuppressWarnings("rawtypes")Box t) {
            return Objects.equals(t.content, o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "(" + content + ")";
    }

}
