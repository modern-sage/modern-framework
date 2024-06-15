package com.modernframework.orm;


/**
 * 带有租户信息的数据
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface ITenantPo<T extends ITenantPo<T>> extends ArIdentifiable<T> {

    /**
     * 租户Id
     */
    Long getTenantId();

    /**
     * 租户Id
     *
     * @param tenantId 租户Id
     * @return <T>
     */
    T setTenantId(Long tenantId);
}
