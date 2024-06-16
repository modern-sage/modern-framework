package com.modernframework.orm;


/**
 * 实现逻辑删除
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface ILogicalPo<T extends ILogicalPo<T>> extends ArIdentifiable<T> {

    /**
     * 逻辑删除标志，状态1表示记录被删除，0表示正常记录
     *
     * @see DataOrmConstant#DELETED
     * @see DataOrmConstant#NOT_DELETED
     */
    Integer getDeleteFlag();

    /**
     * 设置当前实体逻辑删除标志
     * <p>
     * * @see MdRepoConstant#DELETED
     * * @see MdRepoConstant#NOT_DELETED
     *
     * @param deleteFlag 删除标志
     * @return <T>
     */
    T setDeleteFlag(Integer deleteFlag);
}
