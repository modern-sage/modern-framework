package com.modern.security.spring.service;

import com.modern.security.*;
import com.modern.security.spring.UserDetailsAdapter;
import com.modern.security.spring.UserAuthenticationDetails;
import com.modern.security.spring.config.SpringSecurityProperties;
import com.modern.security.spring.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * 授权登录业务实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class DefaultSecurityService implements SecurityService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final SpringSecurityProperties securityProperties;
    private final AuthenticationDetailsService<UserAuthenticationDetails> authDetailsService;

    public DefaultSecurityService(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                                  SpringSecurityProperties securityProperties, AuthenticationDetailsService<UserAuthenticationDetails> authDetailsService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.securityProperties = securityProperties;
        this.authDetailsService = authDetailsService;
    }

    /**
     * 登录用户
     *
     * @param username 用户名
     * @param password 密码
     * @return UserCertificate
     */
    @Override
    public UserCertificate login(String username, String password) {
        //创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        //调用AuthenticationManager的authenticate方法进行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //登录成功以后用户信息、
        UserDetailsAdapter authUser = (UserDetailsAdapter) authentication.getPrincipal();

        long now = System.currentTimeMillis();
        long accessExpireTime = now + securityProperties.getAccessTokenExpire();
        long refreshExpireTime = now + securityProperties.getRefreshTokenExpire();

        UserCertificate certificate = new UserCertificate();
        certificate.setUserId(authUser.getUserId());
        certificate.setUsername(authUser.getUsername());
        certificate.setToken(TokenUtils.createAccessToken());
        certificate.setRefreshToken(TokenUtils.createAccessToken());

        // 存储登陆的详细信息（Token等）
        UserAuthenticationDetails authDetails = new UserAuthenticationDetails();
        authDetails.setAccessToken(certificate.getToken());
        authDetails.setAccessExpireTime(accessExpireTime);
        authDetails.setRefreshToken(certificate.getRefreshToken());
        authDetails.setRefreshExpireTime(refreshExpireTime);
        authDetailsService.saveAuthDetails(authDetails);

        return certificate;
    }

    /**
     * getUserInfo
     */
    @Override
    public AuthenticationUser getUserInfo() {
        return null;
    }

    /**
     * 刷新token
     *
     * @param accessToken  访问令牌
     * @param refreshToken 刷新令牌
     * @return MdCertificate
     */
    @Override
    public UserCertificate refreshToken(String accessToken, String refreshToken) {
        if (!JwtUtils.validateRefreshToken(refreshToken) && !JwtUtils.validateWithoutExpiration(accessToken)) {
            throw new BizException("认证失败");
        }
        Optional<String> userName = JwtUtils.parseRefreshTokenClaims(refreshToken).map(Claims::getSubject);
        if (userName.isPresent()) {
            final UserDetails authUser = userDetailsService.loadUserByUsername(userName.get());
            if (authUser == null) {
                throw new InternalAuthenticationServiceException("认证失败");
            }
            UserCertificate certificate = new UserCertificate();
            certificate.setUsername(authUser.getUsername());
            certificate.setToken(JwtUtils.createAccessToken(authUser, securityProperties.getAccessTokenExpire()));
            certificate.setRefreshToken(JwtUtils.createRefreshToken(authUser, securityProperties.getRefreshTokenExpire()));
            return certificate;
        }
        throw new BizException("认证失败");
    }

    /**
     * 用户登出
     *
     * @return Boolean
     */
    @Override
    public boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
        return authDetailsService.removeAuthDetails(details.getAccessToken());
    }

}
