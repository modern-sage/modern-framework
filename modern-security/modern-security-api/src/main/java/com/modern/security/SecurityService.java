package com.modern.security;


/**
 * SecurityService
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface SecurityService {

    /**
     * 登录用户
     *
     * @param username 用户名
     * @param password 密码
     * @return MdCertificate
     */
    UserCertificate login(String username, String password);

    /**
     * getUserInfo
     */
    AuthenticationUser getUserInfo();

    /**
     * 刷新token
     *
     * @param accessToken  访问令牌
     * @param refreshToken 刷新令牌
     * @return MdCertificate
     */
    UserCertificate refreshToken(String accessToken, String refreshToken);


    /**
     * 用户登出
     *
     * @return Boolean
     */
    boolean logout();


//    /**
//     * 注册用户
//     *
//     * @param username 用户名
//     * @param password 密码
//     * @return UserBo
//     */
//    UserBo register(String username, String password);


}
