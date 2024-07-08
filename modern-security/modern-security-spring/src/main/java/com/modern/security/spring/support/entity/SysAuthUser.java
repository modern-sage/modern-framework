package com.modern.security.spring.support.entity;

import com.modern.orm.mp.IdPo;
import com.modern.security.AuthenticationUser;
import com.modernframework.core.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 内部的认证用户信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysAuthUser extends IdPo<SysAuthUser> implements AuthenticationUser {

    /**
     * 用户登录名
     */
    protected String username;
    /**
     * 用户密码
     */
    protected String password;

    /**
     * 权限集合
     */
    protected String permissions;

    /**
     * 是否启用。1：启用，0：未启用
     */
    protected Integer isEnabled;

    /**
     * 账号是否过期，1：已经过期，0：未过期
     */
    protected Integer isExpired;

    /**
     * 账号是否被锁，1：被锁，0：未被锁
     */
    protected Integer isLocked;

    /**
     * 用户的凭证是否过期。1：已经过期，0：未过期
     */
    protected Integer isCredentialsExpired;

    /**
     * 返回权限
     */
    public Collection<String> getPermissions() {
        return StringUtils.isBlank(permissions)? Collections.emptyList() : Arrays.asList(permissions.split(","));
    }

    @Override
    public Long getUserId() {
        return this.getId();
    }
}
