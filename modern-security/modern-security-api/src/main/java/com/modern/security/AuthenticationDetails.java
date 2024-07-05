package com.modern.security;


/**
 * 认证的其他信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface AuthenticationDetails {

    /**
     * 用户名
     */
    String getUsername();

    /**
     * 访问凭证Token
     */
    String getAccessToken();

    /**
     * 访问凭证过期时间
     */
    Long getAccessExpireTime();

    /**
     * 访问凭证刷新Token
     */
    String getRefreshToken();

    /**
     * 访问凭证刷新过期时间
     */
    Long getRefreshExpireTime();

}
