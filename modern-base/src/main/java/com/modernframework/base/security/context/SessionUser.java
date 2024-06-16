package com.modernframework.base.security.context;


import com.modernframework.base.Identifiable;

/**
 * Session中的User信息接口
 */
public interface SessionUser extends Identifiable<Long> {

    /**
     * 用户Id
     *
     * @return
     */
    @Override
    Long getId();

    /**
     * 用户名
     *
     * @return
     */
    default String getUsername() {
        return null;
    }

    /**
     * 获取租户id，默认不存在为null
     *
     * @return
     */
    default Long getTenantId() {
        return null;
    }

}
