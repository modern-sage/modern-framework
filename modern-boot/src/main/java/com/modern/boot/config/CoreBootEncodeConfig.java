package com.modern.boot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * encoder相关配置
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Configuration
@EnableConfigurationProperties(CoreBootEncodeProperties.class)
public class CoreBootEncodeConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(CoreBootEncodeProperties properties) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding(properties.getCharset());
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

}
