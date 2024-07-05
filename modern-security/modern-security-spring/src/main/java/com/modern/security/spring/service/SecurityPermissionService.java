package com.modern.security.spring.service;

import com.modernframework.core.utils.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * 授权服务
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class SecurityPermissionService {

    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    public boolean hasAnyPermissions(String... permissions) {
        if (CollectionUtils.isEmpty(Arrays.asList(permissions))) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).filter(x -> !x.contains("ROLE_"))
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
    }

    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    public boolean hasAnyRoles(String... roles) {
        if (CollectionUtils.isEmpty(Arrays.asList(roles))) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).filter(x -> x.contains("ROLE_"))
                .anyMatch(x -> PatternMatchUtils.simpleMatch(roles, x));
    }
}
