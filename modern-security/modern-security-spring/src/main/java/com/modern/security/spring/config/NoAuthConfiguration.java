package com.modern.security.spring.config;

import com.modern.security.NoAuth;
import com.modernframework.core.utils.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 不需要认证资源配置类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Component
public class NoAuthConfiguration implements InitializingBean, ApplicationContextAware {

    private static final String PATTERN = "\\{(.*?)}";

    public static final String ASTERISK = "*";

    private ApplicationContext applicationContext;

    private final List<String> permitAllUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        // fix 如下方式：
        // RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 报错信息为：
        // Invocation of init method failed; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException:
        // No qualifying bean of type 'org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping'
        // available: expected single matching bean but found 2: requestMappingHandlerMapping,controllerEndpointHandlerMapping
        RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(x -> {
            HandlerMethod handlerMethod = map.get(x);

            // 获取方法上边的注解 替代path variable 为 *
            NoAuth method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), NoAuth.class);
            if (method != null) {
                x.getPatternValues().stream().map(url -> {
                    if (x.getPathPatternsCondition() != null) {
                        return url.replaceAll(PATTERN, ASTERISK);
                    } else {
                        return url;
                    }
                }).forEach(permitAllUrls::add);
            }

            // 获取类上边的注解, 替代path variable 为 *
            NoAuth controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), NoAuth.class);
            if (controller != null) {
                x.getPatternValues().stream().map(url -> {
                    if (x.getPathPatternsCondition() != null) {
                        return url.replaceAll(PATTERN, ASTERISK);
                    } else {
                        return url;
                    }
                }).forEach(permitAllUrls::add);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        SpringSecurityProperties properties = this.applicationContext.getBean(SpringSecurityProperties.class);
        if(ArrayUtils.isNotEmpty(properties.getPermitUrls())) {
            this.permitAllUrls.addAll(Arrays.asList(properties.getPermitUrls()));
        }
    }

    public List<String> getPermitAllUrls() {
        return permitAllUrls;
    }

}
