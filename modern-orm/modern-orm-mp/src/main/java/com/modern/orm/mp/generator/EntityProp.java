package com.modern.orm.mp.generator;

import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * 实体配置参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class EntityProp {

    /**
     * 当前库反射需要的表名
     */
    private List<String> includes;

    /**
     * 当前库反射排除的表名
     */
    private List<String> excludes;

    /**
     * 默认填充公共字段
     */
    private List<IFill> fills;

    /**
     * 数据库连接
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 输出目录
     */
    private String output;

    /**
     * 作者
     */
    private String author;

    /**
     * 父包名
     */
    private String packageName;

    /**
     * 路径配置信息
     */
    private Map<OutputFile, String> pathInfo;

    /**
     * 实体指定父类
     */
    private Class entitySuperClass;

    /**
     * Service指定父类
     */
    private Class serviceSuperClass;

    /**
     * ServiceImpl实现类指定父类
     */
    private Class serviceImplSuperClass;

    /**
     * Mapper指定父类
     */
    private Class mapperSuperClass;

    /**
     * controller指定父类
     */
    private Class controllerSuperClass;

}
