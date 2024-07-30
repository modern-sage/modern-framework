package com.modernframework.base.criteria;

/**
 * and or
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class Relationship implements CriteriaExpress {

    private final String relation;

    public Relationship(String relation) {
        this.relation = relation;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Relationship other)) {
            return false;
        }
        return relation.equals(other.relation);
    }

    @Override
    public String toString() {
        return " " + relation + " ";
    }
}
