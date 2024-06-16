package com.modernframework.orm;


/**
 * <pre>
 * 记录版本，实现乐观锁
 * </pre>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface IVersionPo<T extends IVersionPo<T>> extends ArIdentifiable<T> {

    /**
     * 版本号
     */
    Integer getVersion();

    /**
     * 当前实体乐观锁版本
     *
     * @param version 版本值
     * @return <T>
     */
    T setVersion(Integer version);
}
