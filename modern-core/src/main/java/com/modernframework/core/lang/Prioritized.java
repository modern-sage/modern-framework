package com.modernframework.core.lang;

import java.util.Comparator;

import static java.lang.Integer.compare;

/**
 * 优先级接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Prioritized extends Comparable<Prioritized> {

    /**
     * 优先级接口比较器
     */
    Comparator<Object> COMPARATOR = (one, two) -> {
        boolean b1 = one instanceof Prioritized;
        boolean b2 = two instanceof Prioritized;
        if (b1 && !b2) {
            return -1;
        } else if (b2 && !b1) {
            return 1;
        } else if (b1) {
            return ((Prioritized) one).compareTo((Prioritized) two);
        } else {
            return PriorityComparator.INSTANCE.compare(one, two);
        }
    };

    /**
     * 最大优先级
     */
    int MAX_PRIORITY = Integer.MIN_VALUE;

    /**
     * 最小优先级
     */
    int MIN_PRIORITY = Integer.MAX_VALUE;

    /**
     * 正常的优先级
     */
    int NORMAL_PRIORITY = 0;

    /**
     * 获取优先级，默认是正常的优先级
     */
    default int getPriority() {
        return NORMAL_PRIORITY;
    }

    @Override
    default int compareTo(Prioritized that) {
        return compare(this.getPriority(), that.getPriority());
    }
}
