package com.modern.security.spring.filter;

import com.modern.security.AuthenticationDetails;
import com.modern.security.AuthenticationDetailsService;
import com.modern.security.spring.SecurityConstant;
import com.modern.security.spring.util.JwtUtils;
import com.modernframework.core.convert.ConvertUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.modern.security.SecurityConstant.KEY_AUTHORITIES;


/**
 * 权限验证过滤器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ConvertUtils.class);

    private final String tokenKey;

    private final AuthenticationDetailsService authDetailsService;

    public AuthenticationTokenFilter(AuthenticationDetailsService authDetailsService, String tokenKey) {
        this.authDetailsService = authDetailsService;
        this.tokenKey = tokenKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //解析token中的认证信息
        String jwtTokenString = getJwtTokenStr(request);
        Optional<Claims> claimsOptional = getJwtClaims(jwtTokenString)
                .filter(claims -> claims.get(KEY_AUTHORITIES) != null);

        // 满足下面两个条件代表token正常
        // 1. token解析成功
        // 2. token仓储中存在该token
        if (claimsOptional.isPresent()) {
            // 查询仓库中是否存在改 访问凭证
            final AuthenticationDetails authDetailsByAccessToken = authDetailsService.getAuthDetailsByAccessToken(jwtTokenString);
            if (authDetailsByAccessToken != null) {
                List<String> authoritiesList = castList(claimsOptional.get().get(KEY_AUTHORITIES), String.class);
                // 权限集合
                List<SimpleGrantedAuthority> authorities = authoritiesList
                        .stream().map(String::valueOf)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                // 创建
                // todo 考虑自己实现一个替代 UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(claimsOptional.get().getSubject(), null, authorities);
                usernamePasswordAuthenticationToken.setDetails(authDetailsByAccessToken);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 从 req 中获取 JWT 字符串 <br/>
     * - 从 header 中获取 <br/>
     * - 从 请求参数中获取 <br/>
     *
     * @param req 请求
     * @return Claims
     */
    private String getJwtTokenStr(HttpServletRequest req) throws AuthenticationException {
        String jwtToken = req.getHeader(tokenKey);
        if (jwtToken == null) {
            jwtToken = req.getParameter(tokenKey);
        }
        return jwtToken;
    }

    /**
     * @param jwtToken jwtToken
     * @return Claims
     */
    private Optional<Claims> getJwtClaims(String jwtToken) throws AuthenticationException {
        try {
            return JwtUtils.parseAccessTokenClaims(jwtToken);
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            //输出日志
            log.error("请求获取token解析失败");
            return Optional.empty();
        }
    }
}
