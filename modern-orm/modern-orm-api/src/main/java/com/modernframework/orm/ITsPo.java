package com.modernframework.orm;


import java.time.LocalDateTime;

/**
 * Ts - Time stamp - 记录时间戳
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface ITsPo<T extends ITsPo<T>> extends ArIdentifiable<T> {

    /**
     * 当前实体创建时间
     *
     * @return
     */
    LocalDateTime getCreateDate();

    /**
     * 当前实体创建时间
     *
     * @param createDate 时间值
     * @return
     */
    T setCreateDate(LocalDateTime createDate);

    /**
     * 当前实体更新时间
     *
     * @return
     */
    LocalDateTime getUpdateDate();

    /**
     * 当前实体更新时间
     *
     * @param updateDate 时间值
     * @return
     */
    T setUpdateDate(LocalDateTime updateDate);
}
