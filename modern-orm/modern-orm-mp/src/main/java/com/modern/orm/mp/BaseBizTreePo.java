package com.modern.orm.mp;

import lombok.Getter;


/**
 * BaseBizTreePo
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class BaseBizTreePo<T extends BaseBizTreePo<T>> extends BaseBizPo<T> {

    /**
     * 父节点id
     */
    @Getter
    protected Long parentId;

    /**
     * 父节点路径
     */
    @Getter
    protected String parentIdPath;

    public T setParentId(Long parentId) {
        this.parentId = parentId;
        return (T) this;
    }

    public T setParentIdPath(String parentIdPath) {
        this.parentIdPath = parentIdPath;
        return (T) this;
    }

}
