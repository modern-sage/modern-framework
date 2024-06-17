package com.modern.boot.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;

/**
 * <pre>
 *  CoreBootConfiguration
 * </pre>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
        CoreBootProperties.class,
        CoreBootInterceptorProperties.class,
        CoreBootFilterProperties.class,
        CoreBootCorsProperties.class
})
@ComponentScan("com.modern.boot")
public class CoreBootConfiguration {

    /**
     * CORS跨域设置
     * <p>
     * fix: 解决spring security与corsFilter冲突的问题 <br/>
     * Failed to instantiate [org.springframework.security.web.SecurityFilterChain]: Factory method 'managementSecurityFilterChain' threw exception; nested exception is org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'corsFilter' is expected to be of type 'org.springframework.web.filter.CorsFilter' but was actually of type 'org.springframework.boot.web.servlet.FilterRegistrationBean'
     * //todo 待测试
     *
     * @return FilterRegistrationBean
     */
    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX + ".cors", value = {"enable"}, havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean corsFilter(CoreBootCorsProperties corsProperties) {
        log.debug("corsProperties:{}", corsProperties);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = corsConfiguration(corsProperties);
        source.registerCorsConfiguration(corsProperties.getPath(), corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setEnabled(corsProperties.isEnable());
        return bean;
    }

    @Bean
    @ConditionalOnProperty(prefix = PROPERTIES_PREFIX + ".cors", value = {"enable"}, havingValue = "true", matchIfMissing = true)
    public CorsConfiguration corsConfiguration(CoreBootCorsProperties corsProperties) {
        log.debug("corsProperties:{}", corsProperties);
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 跨域配置
        corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        corsConfiguration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        corsConfiguration.setAllowedMethods(corsProperties.getAllowedMethods());
        corsConfiguration.setAllowCredentials(corsProperties.isAllowCredentials());
        corsConfiguration.setExposedHeaders(corsProperties.getExposedHeaders());
        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());

        return corsConfiguration;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 配置jackson，处理java.time.LocalDate等日期类型
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
