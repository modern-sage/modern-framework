package com.modern.security.spring.service;

import com.modern.base.mvc.service.AbstractBaseBizService;
import com.modern.security.AuthenticationUser;
import com.modern.security.spring.UserDetailsAdapter;
import com.modern.security.spring.util.SecurityAsserts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * UserDetailsService 抽象实现
 *
 * @author zhangj
 */
public abstract class AbstractUserDetailsService extends AbstractBaseBizService implements UserDetailsService {

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthenticationUser authUser = loadUserByUsernameInternal(username);
        SecurityAsserts.assertUserNotNull(authUser);
        return new UserDetailsAdapter(authUser);
    }

    /**
     * 内部的加载用户信息
     *
     * @param username
     * @return
     */
    protected abstract AuthenticationUser loadUserByUsernameInternal(String username);

}
