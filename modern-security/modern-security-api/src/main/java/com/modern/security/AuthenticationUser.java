package com.modern.security;

import java.util.Collection;

import static com.modern.security.SecurityConstant.NO;
import static com.modern.security.SecurityConstant.YES;


/**
 * 认证的用户信息接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface AuthenticationUser {

    /**
     * 用户id
     */
    Long getUserId();

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    String getPassword();

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    String getUsername();

    /**
     * 权限集合
     */
    Collection<String> getPermissions();

    /**
     * 是否启用。1：启用，0：未启用
     */
    Integer getIsEnabled();

    /**
     * 账号是否过期，1：已经过期，0：未过期
     */
    Integer getIsExpired();

    /**
     * 账号是否被锁，1：被锁，0：未被锁
     */
    Integer getIsLocked();

    /**
     * 用户的凭证是否过期。1：已经过期，0：未过期
     */
    Integer getIsCredentialsExpired();

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    default boolean isAccountNonExpired() {
        return getIsExpired().equals(NO);
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    default boolean isAccountNonLocked() {
        return getIsLocked().equals(NO);
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    default boolean isCredentialsNonExpired() {
        return getIsCredentialsExpired().equals(NO);
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    default boolean isEnabled() {
        return getIsEnabled().equals(YES);
    }

}
