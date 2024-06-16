package com.modernframework.orm;


import com.modernframework.core.utils.StringUtils;

/**
 * 具有上级节点的数据
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface ITreePo<T extends ITreePo<T>> extends ArIdentifiable<T> {
    
    /**
     * 上级节点Id
     */
    Long getParentId();

    T setParentId(Long parentId);

    /**
     * 上级节点路径
     */
    String getParentIdPath();

    T setParentIdPath(String parentIdPath);

    /**
     * 获取完整的Id路径
     *
     * @return String
     */
    default String getAllIdPath() {
        return StringUtils.blankToDefault(getParentIdPath(), "") + getId();
    }

    @Override
    default PoType getPoType() {
        return PoType.TREE;
    }
}
