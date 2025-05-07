package com.modern.security.spring.support.entity;

import com.modern.orm.mp.IdPo;
import com.modern.security.AuthenticationDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 存储有关身份验证请求的其他详细信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysAuthDetails extends IdPo<SysAuthDetails> implements AuthenticationDetails {

    public static final String TABLE_NAME = "sys_auth_details";

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 访问凭证Token
     */
    private String accessToken;

    /**
     * 访问凭证过期时间
     */
    private Long accessExpireTime;

    /**
     * 访问凭证刷新Token
     */
    private String refreshToken;

    /**
     * 访问凭证刷新过期时间
     */
    private Long refreshExpireTime;

    /**
     * 权限集合
     */
    private String permissions;

}
