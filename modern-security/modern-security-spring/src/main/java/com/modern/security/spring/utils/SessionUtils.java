package com.modern.security.spring.utils;

import com.modern.security.AuthenticationDetails;
import com.modern.security.spring.DefaultSessionUser;

/**
 * SessionUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public abstract class SessionUtils {

    public static DefaultSessionUser convertDefaultSessionUser(AuthenticationDetails authenticationDetails) {
        DefaultSessionUser sessionUser = new DefaultSessionUser();
        sessionUser.setId(authenticationDetails.getUserId());
        sessionUser.setUsername(authenticationDetails.getUsername());
        sessionUser.setAccessToken(authenticationDetails.getAccessToken());
        sessionUser.setAccessExpireTime(authenticationDetails.getAccessExpireTime());
        sessionUser.setRefreshToken(authenticationDetails.getRefreshToken());
        sessionUser.setRefreshExpireTime(authenticationDetails.getRefreshExpireTime());
        return sessionUser;
    }

}
