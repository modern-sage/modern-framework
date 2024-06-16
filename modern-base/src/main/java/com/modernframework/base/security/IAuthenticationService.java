package com.modernframework.base.security;

import java.io.Serializable;

/**
 * 权限检查接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface IAuthenticationService {

    /**
     * 确认登录状态
     *
     * @return boolean
     * @throws AuthException
     */
    boolean checklogin() throws AuthException;

    /**
     * 是否是本人操作
     *
     * @return boolean
     * @throws AuthException
     */
    boolean owner(Serializable id) throws AuthException;

    /**
     * 是否有权限
     *
     * @param perssions
     * @return boolean
     * @throws AuthException
     */
    boolean hasPerssion(String... perssions) throws AuthException;

}
