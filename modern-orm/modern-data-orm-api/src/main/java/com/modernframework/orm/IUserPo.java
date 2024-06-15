package com.modernframework.orm;


/**
 * <pre>
 *     操作人记录，包含当前记录操作人信息
 * </pre>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface IUserPo<T extends IUserPo<T>> extends ArIdentifiable<T> {
    /**
     * 记录生成人的ID
     */
    Long getCreateUserId();

    T setCreateUserId(Long createUserId);

    /**
     * 最后一个更新人的ID
     */
    Long getUpdateUserId();

    T setUpdateUserId(Long updateUserId);


}
