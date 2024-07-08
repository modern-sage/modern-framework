package com.modern.security.spring.support.service.impl;

import com.modern.security.AuthenticationUser;
import com.modern.security.spring.service.AbstractUserDetailsService;
import com.modern.security.spring.support.entity.SysAuthUser;
import com.modern.security.spring.support.service.SysAuthUserService;
import com.modernframework.base.criteria.GrateParam;

/**
 * DefaultSysAuthUserServiceImpl
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class DefaultUserDetailsServiceImpl extends AbstractUserDetailsService {

    private final SysAuthUserService sysAuthUserService;

    public DefaultUserDetailsServiceImpl(SysAuthUserService sysAuthUserService) {
        this.sysAuthUserService = sysAuthUserService;
    }

    /**
     * 内部的加载用户信息
     */
    @Override
    protected AuthenticationUser loadUserByUsernameInternal(String username) {
        return sysAuthUserService.one(new GrateParam<SysAuthUser>().eq(SysAuthUser::getUsername, username));
    }
}
