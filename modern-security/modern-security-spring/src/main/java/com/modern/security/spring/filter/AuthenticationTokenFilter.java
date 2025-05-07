package com.modern.security.spring.filter;

import com.modern.security.AuthenticationDetails;
import com.modern.security.AuthenticationDetailsService;
import com.modern.security.spring.utils.SessionUtils;
import com.modernframework.base.security.context.UserContext;
import com.modernframework.core.convert.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 权限验证过滤器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ConvertUtils.class);

    private final String tokenKey;

    private final AuthenticationDetailsService authenticationDetailsService;

    public AuthenticationTokenFilter(AuthenticationDetailsService authenticationDetailsService, String tokenKey) {
        this.authenticationDetailsService = authenticationDetailsService;
        this.tokenKey = tokenKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //解析token中的认证信息
        String tokenKey = getTokenKey(request);
        final AuthenticationDetails authDetailsByAccessToken = authenticationDetailsService.getAuthDetailsByAccessToken(tokenKey);
        if (authDetailsByAccessToken != null) {
            //TODO 权限集合
            List<String> authoritiesList = Collections.emptyList();
            List<SimpleGrantedAuthority> authorities = authoritiesList.stream().map(String::valueOf)
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            // 创建
            // todo 考虑自己实现一个替代 UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(authDetailsByAccessToken.getUsername(), null, authorities);
            usernamePasswordAuthenticationToken.setDetails(authDetailsByAccessToken);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            UserContext.setUser(SessionUtils.convertDefaultSessionUser(authDetailsByAccessToken));
        } else {
            SecurityContextHolder.clearContext();
            UserContext.clear();
        }
        chain.doFilter(request, response);
    }

    /**
     * 从 req 中获取 Token 字符串 <br/>
     * - 从 header 中获取 <br/>
     * - 从 请求参数中获取 <br/>
     *
     * @param req 请求
     */
    private String getTokenKey(HttpServletRequest req) throws AuthenticationException {
        String tokenKey = req.getHeader(this.tokenKey);
        if (tokenKey == null) {
            tokenKey = req.getParameter(this.tokenKey);
        }
        return tokenKey;
    }

}
