package com.modern.boot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Arrays;
import java.util.List;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;


/**
 * spring-boot-plus属性配置信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX)
public class CoreBootProperties {

    /**
     * 是否启用ansi控制台输出有颜色的字体，local环境建议开启，服务器环境设置为false
     */
    private boolean enableAnsi;

    /**
     * 拦截器配置
     */
    @NestedConfigurationProperty
    private CoreBootInterceptorProperties interceptor;

    /**
     * 过滤器配置
     */
    @NestedConfigurationProperty
    private CoreBootFilterProperties filter;

    /**
     * 允许上传的文件后缀集合
     */
    private List<String> allowUploadFileExtensions = Arrays.asList("jpg,png,docx,xlsx,pptx,pdf".split(","));
    /**
     * 允许下载的文件后缀集合
     */
    private List<String> allowDownloadFileExtensions = Arrays.asList("jpg,png,docx,xlsx,pptx,pdf".split(","));

//    /**
//     * 项目静态资源访问配置
//     */
//    private String resourceHandlers;

    /**
     * 开启后台轮训自动服务
     */
    @Value("${modern.boot.openBackAutoService: true}")
    private boolean openBackAutoService;


}
