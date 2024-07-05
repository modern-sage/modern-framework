package com.modern.security;


/**
 * AuthenticationDetailsService
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface AuthenticationDetailsService<T extends AuthenticationDetails> {

    /**
     * 根据访问凭证获取登陆的其他信息
     *
     * @param accessToken 访问凭证
     */
    T getAuthDetailsByAccessToken(String accessToken);

    /**
     * 保存
     */
    boolean saveAuthDetails(T authDetails);

    /**
     * 删除
     */
    boolean removeAuthDetails(String accessToken);

}
