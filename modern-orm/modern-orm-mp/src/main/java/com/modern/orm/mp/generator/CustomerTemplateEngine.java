package com.modern.orm.mp.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自定义模板引擎
 */
public class CustomerTemplateEngine extends FreemarkerTemplateEngine {

    /**
     * 项目定制组件父类模板
     */
    public static String customService = "ICustomService.java";
    /**
     * 项目定制组件父类实现类模板
     */
    public static String customImplService = "CustomServiceImpl.java";
    /**
     * 项目定制业务父类模板
     */
    public static String bizService = "IBizService.java";
    /**
     * 项目定制业务父类抽象类模板
     */
    public static String AbstractBizService = "AbstractBizService.java";

    /**
     * 输出自定义模板文件
     *
     * @param customFile 自定义配置模板文件信息
     * @param tableInfo  表信息
     * @param objectMap  渲染数据
     */
    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String otherPath = getPathInfo(OutputFile.other);
        AtomicBoolean customServiceJavaDone = new AtomicBoolean(false);
        AtomicBoolean customServiceImplJavaDone = new AtomicBoolean(false);
        AtomicBoolean bizServiceJavaDone = new AtomicBoolean(false);
        AtomicBoolean abstractBizServiceJavaDone = new AtomicBoolean(false);
        customFile.forEach((key, value) -> {
            if (key.equals(customService)) {
                if (customServiceJavaDone.get()) {
                    return;
                }
                String customPath = getPathInfo(OutputFile.service) + File.separator + "base";
                String fileName = String.format((customPath + File.separator + "%s"), key);
                outputFile(new File(fileName), objectMap, value);
                customServiceJavaDone.set(true);
            } else if (key.equals(customImplService)) {
                if (customServiceImplJavaDone.get()) {
                    return;
                }
                String customPath = getPathInfo(OutputFile.service) + File.separator + "base";
                String fileName = String.format((customPath + File.separator + "%s"), key);
                outputFile(new File(fileName), objectMap, value);
                customServiceImplJavaDone.set(true);
            } else if (key.equals(bizService)) {
                if (bizServiceJavaDone.get()) {
                    return;
                }
                String customPath = getPathInfo(OutputFile.service) + File.separator + "base";
                String fileName = String.format((customPath + File.separator + "%s"), key);
                outputFile(new File(fileName), objectMap, value);
                bizServiceJavaDone.set(true);
            } else if (key.equals(AbstractBizService)) {
                if (abstractBizServiceJavaDone.get()) {
                    return;
                }
                String customPath = getPathInfo(OutputFile.service) + File.separator + "base";
                String fileName = String.format((customPath + File.separator + "%s"), key);
                outputFile(new File(fileName), objectMap, value);
                abstractBizServiceJavaDone.set(true);
            } else {
                String fileName = String.format((otherPath + File.separator + entityName + File.separator + "%s"), key);
                outputFile(new File(fileName), objectMap, value);
            }
        });
    }
}
