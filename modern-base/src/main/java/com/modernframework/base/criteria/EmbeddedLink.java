package com.modernframework.base.criteria;

/**
 * 内嵌关系
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class EmbeddedLink implements CriteriaExpress {

    private final String symbol;

    public EmbeddedLink(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return " " + symbol + " ";
    }

}
