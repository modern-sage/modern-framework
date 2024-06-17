package com.modern.orm.mp.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.modern.orm.mp.mapper.MdBaseMapper;
import com.modern.orm.mp.service.AbstractBaseService;
import com.modern.orm.mp.utils.RepoMpUtils;
import com.modernframework.base.service.BaseService;
import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.ReflectUtils;
import com.modernframework.core.utils.StringUtils;
import com.modernframework.orm.PoType;
import org.apache.ibatis.cache.Cache;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.modernframework.base.constant.BaseConstant.DATE_FORMAT;


public class MdGenerator {

    private final String TEMPLATE_PATH_PRE = "/repoTemplates";


    /**
     * 生成表前缀
     */
    List<String> prefixs = new ArrayList<>();

    /**
     * 数据库表生成实体
     *
     * @param includes    当前库反射需要的表名
     * @param excludes    当前库反射排除的表名
     * @param url         数据库连接
     * @param root        用户名
     * @param password    密码
     * @param output      输出目录
     * @param author      作者
     * @param packageName 父包名
     * @param pathInfo    路径配置信息
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String url, String root, String password,
                       String output, String author, String packageName, Map<OutputFile, String> pathInfo, PoType poType) {
        return gen(includes, excludes, url, root, password,
                output, author, packageName, pathInfo, RepoMpUtils.getPoClass(poType), poType);
    }

    /**
     * 数据库表生成实体
     *
     * @param includes         当前库反射需要的表名
     * @param excludes         当前库反射排除的表名
     * @param url              数据库连接
     * @param root             用户名
     * @param password         密码
     * @param output           输出目录
     * @param author           作者
     * @param packageName      父包名
     * @param pathInfo         路径配置信息
     * @param entitySuperClass 实体指定父类
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String url, String root, String password,
                       String output, String author, String packageName, Map<OutputFile, String> pathInfo,
                       Class entitySuperClass, PoType poType ) {
        Field[] fs = ReflectUtils.getFields(entitySuperClass);
        List<IFill> fills = getCommons(Arrays.asList(fs).stream().map(it -> it.getName()).collect(Collectors.toList()));
        return gen(includes, excludes, fills, url, root, password,
                output, author, packageName, pathInfo, entitySuperClass,
                poType);
    }

    /**
     * 数据库表生成实体
     *
     * @param includes         当前库反射需要的表名
     * @param excludes         当前库反射排除的表名
     * @param fills            默认填充公共字段
     * @param url              数据库连接
     * @param root             用户名
     * @param password         密码
     * @param output           输出目录
     * @param author           作者
     * @param packageName      父包名
     * @param pathInfo         路径配置信息
     * @param entitySuperClass 实体指定父类
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, List<IFill> fills, String url, String root, String password,
                       String output, String author, String packageName, Map<OutputFile, String> pathInfo,
                       Class entitySuperClass, PoType poType ) {
        return gen(includes, excludes, fills, url, root, password,
                output, author, packageName, pathInfo, entitySuperClass,
                BaseService.class,
                AbstractBaseService.class,
                MdBaseMapper.class, null);
    }

//    /**
//     * 数据库表生成实体
//     *
//     * @param includes         当前库反射需要的表名
//     * @param excludes         当前库反射排除的表名
//     * @param fills            默认填充公共字段
//     * @param url              数据库连接
//     * @param root             用户名
//     * @param password         密码
//     * @param output           输出目录
//     * @param author           作者
//     * @param packageName      父包名
//     * @param pathInfo         路径配置信息
//     * @param entitySuperClass 实体指定父类
//     * @return
//     */
//    public boolean gen(List<String> includes, List<String> excludes, List<IFill> fills, String url, String root, String password,
//                       String output, String author, String packageName, Map<OutputFile, String> pathInfo,
//                       Class entitySuperClass) {
//        return gen(includes, excludes, fills, url, root, password,
//                output, author, packageName, pathInfo, entitySuperClass,
//                IMdService.class, FlineServiceImpl.class, FlineMapper.class, null);
//    }

    /**
     * 数据库表生成实体
     *
     * @param includes              当前库反射需要的表名
     * @param excludes              当前库反射排除的表名
     * @param fills                 默认填充公共字段
     * @param url                   数据库连接
     * @param root                  用户名
     * @param password              密码
     * @param output                输出目录
     * @param author                作者
     * @param packageName           父包名
     * @param pathInfo              路径配置信息
     * @param entitySuperClass      实体指定父类
     * @param serviceSuperClass     Service指定父类
     * @param serviceImplSuperClass ServiceImpl实现类指定父类
     * @param mapperSuperClass      Dao指定父类
     * @param entitySuperClass      controller指定父类
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, List<IFill> fills, String url, String root,
                       String password, String output, String author, String packageName,
                       Map<OutputFile, String> pathInfo, Class entitySuperClass, Class serviceSuperClass,
                       Class serviceImplSuperClass, Class mapperSuperClass, Class controllerSuperClass) {
        AutoGenerator generator = new AutoGenerator(SqlGenerator.db(url, root, password))
                .global(SqlGenerator.globalAll(true, true, output, author, false, true, DateType.TIME_PACK, DATE_FORMAT))
                .template(SqlGenerator.templateAll(TEMPLATE_PATH_PRE + "/entity.java",
                        TEMPLATE_PATH_PRE + "/service.java",
                        TEMPLATE_PATH_PRE + "/serviceImpl.java",
                        TEMPLATE_PATH_PRE + "/mapper.java",
                        TEMPLATE_PATH_PRE + "/mapper.xml",
                        TEMPLATE_PATH_PRE + "/controller.java",
                        false, null))
                .packageInfo(SqlGenerator.pack(packageName, pathInfo))
                // 不需要补充的服务
//                .injection(customConfig())
                .strategy(config(includes, excludes, fills, controllerSuperClass, serviceSuperClass, serviceImplSuperClass, mapperSuperClass, entitySuperClass, null));
        generator.execute(new CustomerTemplateEngine());
        return true;
    }


    /**
     * 数据库表生成实体
     *
     * @param includes         当前库反射需要的表名
     * @param excludes         当前库反射排除的表名
     * @param commonFields     公共字段
     * @param url              数据库连接
     * @param root             用户名
     * @param password         密码
     * @param output           输出目录
     * @param author           作者
     * @param packageName      父包名
     * @param pathInfo         路径配置信息
     * @param entitySuperClass 实体指定父类
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String[] commonFields,
                       String url, String root, String password, String output, String author, String packageName,
                       Map<OutputFile, String> pathInfo, Class entitySuperClass, PoType poType) {
        List<IFill> fills = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(commonFields)) {
            fills = Arrays.asList(commonFields).stream().map(f -> new Property(f, FieldFill.INSERT)).collect(Collectors.toList());
        }
        return gen(includes, excludes, fills, url, root, password, output, author,
                packageName, pathInfo, entitySuperClass, poType);
    }

    /**
     * 数据库表生成实体
     *
     * @param url         数据库连接
     * @param root        用户名
     * @param password    密码
     * @param output      输出目录
     * @param author      作者
     * @param packageName 父包名
     * @param pathInfo    路径配置信息
     * @return
     */
    public boolean gen(String url, String root, String password, String output, String author,
                       String packageName, Map<OutputFile, String> pathInfo, PoType poType) {
        List<String> excludes = new ArrayList<String>() {
            {
                add("");
            }
        };
        return gen(null, excludes, url, root, password, output, author, packageName, pathInfo, poType);
    }

    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param url         数据库连接
     * @param root        用户名
     * @param password    密码
     * @param author      作者
     * @param packageName 父包名
     * @return
     */
    public boolean gen(String url, String root, String password, String author, String packageName, PoType poType ) {
        return gen(url, root, password, author, packageName, null, poType);
    }

    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param url         数据库连接
     * @param root        用户名
     * @param password    密码
     * @param author      作者
     * @param packageName 父包名
     * @param moduleName  模块相对根模块路径名称
     * @return
     */
    public boolean gen(String url, String root, String password, String author, String packageName, String moduleName, PoType poType) {
        return gen(null, null, url, root, password, author, packageName, moduleName, poType);
    }

    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param includes    当前库反射需要的表名
     * @param excludes    当前库反射排除的表名
     * @param url         数据库连接
     * @param root        用户名
     * @param password    密码
     * @param author      作者
     * @param packageName 父包名
     * @param moduleName  模块相对根模块路径名称
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String url, String root, String password, String author,
                       String packageName, String moduleName, PoType poType ) {
        return gen(includes, excludes, url, root, password, author, packageName, moduleName,
                RepoMpUtils.getPoClass(poType), poType);
    }


    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param includes    当前库反射需要的表名
     * @param excludes    当前库反射排除的表名
     * @param url         数据库连接
     * @param root        用户名
     * @param password    密码
     * @param author      作者
     * @param packageName 父包名
     * @param moduleName  模块相对根模块路径名称
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String url, String root, String password, String author,
                       String packageName, String moduleName,  Class entitySuperClass) {
        return gen(includes, excludes, url, root, password, author, packageName, moduleName,
                entitySuperClass, null);
    }

    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param includes         当前库反射需要的表名
     * @param excludes         当前库反射排除的表名
     * @param url              数据库连接
     * @param root             用户名
     * @param password         密码
     * @param author           作者
     * @param packageName      父包名
     * @param moduleName       模块相对根模块路径名称
     * @param entitySuperClass 实体指定父类
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String url, String root, String password,
                       String author, String packageName, String moduleName, Class entitySuperClass, PoType poType) {
        return gen(includes, excludes, url, root, password, output(moduleName), author, packageName,
                Collections.singletonMap(OutputFile.mapperXml, path(moduleName, packageName)), entitySuperClass,
                poType);
    }

    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param includes         当前库反射需要的表名
     * @param excludes         当前库反射排除的表名
     * @param commonFields     公共字段
     * @param url              数据库连接
     * @param root             用户名
     * @param password         密码
     * @param author           作者
     * @param packageName      父包名
     * @param moduleName       模块相对根模块路径名称
     * @param entitySuperClass 实体指定父类
     * @return
     */
    public boolean gen(List<String> includes, List<String> excludes, String[] commonFields, String url, String root, String password, String author,
                       String packageName, String moduleName, Class entitySuperClass, PoType poType) {
        return gen(includes, excludes, commonFields, url, root, password, output(moduleName), author, packageName,
                Collections.singletonMap(OutputFile.mapperXml, path(moduleName, packageName)), entitySuperClass, poType);
    }

    /**
     * 数据库表生成实体,默认将JAVA相关文件输出到当前项目的src/main/java中，XML文件输出/src/main/resources/$packagename/repo/persistence下
     *
     * @param includes         当前库反射需要的表名
     * @param excludes         当前库反射排除的表名
     * @param fills            默认填充公共字段
     * @param url              数据库连接
     * @param root             用户名
     * @param password         密码
     * @param author           作者
     * @param packageName      父包名
     * @param moduleName       模块相对根模块路径名称
     * @param entitySuperClass 实体指定父类
     * @return boolean
     */
    public boolean gen(List<String> includes, List<String> excludes, List<IFill> fills, String url, String root, String password,
                       String author, String packageName, String moduleName, Class entitySuperClass, PoType poType ) {
        return gen(includes, excludes, fills, url, root, password, output(moduleName), author, packageName,
                Collections.singletonMap(OutputFile.mapperXml, path(moduleName, packageName)), entitySuperClass, poType);
    }

    /**
     * 根据模块名称和报名获取文件路径
     *
     * @param moduleName  模块名称
     * @param packageName 包名
     * @return
     */
    private String path(String moduleName, String packageName) {
        String persistence = "/persistence";
        if (StringUtils.isNotBlank(moduleName)) {
            if(moduleName.contains("/")) {
                persistence = moduleName.substring(moduleName.lastIndexOf("/"));
            } else {
                persistence = moduleName;
            }
        }
        String path = "src/main/resources/mapper/" + persistence;
        if (StringUtils.isNotBlank(moduleName)) {
            path = moduleName + "/" + path;
        }
        return path;
    }

    /**
     * 根据模块名称h获取输出路径
     *
     * @param moduleName 模块路径
     * @return
     */
    private String output(String moduleName) {
        String output = "src/main/java";
        if (StringUtils.isNotBlank(moduleName)) {
            output = moduleName + "/" + output;
        }
        return output;
    }

    /**
     * 自定义公共服务类
     *
     * @return
     */
    InjectionConfig customConfig() {
        Map<String, String> customMap = new HashMap<>();
        customMap.put(CustomerTemplateEngine.customService, TEMPLATE_PATH_PRE + "/customService.java.ftl");
        customMap.put(CustomerTemplateEngine.customImplService, TEMPLATE_PATH_PRE + "/customServiceImpl.java.ftl");
        customMap.put(CustomerTemplateEngine.bizService, TEMPLATE_PATH_PRE + "/bizService.java.ftl");
        customMap.put(CustomerTemplateEngine.AbstractBizService, TEMPLATE_PATH_PRE + "/AbstractBizService.java.ftl");
        return new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                })
                .customMap(Collections.singletonMap("", ""))
                .customFile(customMap)
                .build();
    }

    /**
     * TODO FIXME
     * 设置公共字段策略
     *
     * @return
     */
    protected Map<String, FieldFill> properties() {
        return new HashMap<String, FieldFill>() {
            {
                put("name", FieldFill.DEFAULT);
                put("code", FieldFill.DEFAULT);
                put("deleteFlag", FieldFill.INSERT);
                put("status", FieldFill.INSERT);
                put("updateTime", FieldFill.INSERT);
                put("updateDate", FieldFill.INSERT_UPDATE);
                put("createDate", FieldFill.INSERT);
                put("version", FieldFill.INSERT);
                put("memo", FieldFill.DEFAULT);
                put("creator", FieldFill.INSERT);
                put("updator", FieldFill.INSERT);
            }
        };
    }

    /**
     * 获取支持的公有字段
     *
     * @param commons
     * @return
     */
    List<IFill> getCommons(List<String> commons) {
        Map<String, FieldFill> properties = properties();
        return properties.entrySet().stream().filter(it -> commons.contains(it.getKey())).map(it -> new Property(it.getKey(), it.getValue())).collect(Collectors.toList());
    }


    /**
     * 设置表前缀
     *
     * @param prefixs
     * @return
     */
    public MdGenerator prefixs(List<String> prefixs) {
        this.prefixs = prefixs;
        return this;
    }

    /**
     * 快速策略配置
     *
     * @param includes              包含表名
     * @param exludes               排除表名
     * @param fills                 自动填充字段
     * @param controllerSuperClass  控制接口父类
     * @param serviceSuperClass     业务接口父类
     * @param serviceImplSuperClass 业务实现类父类
     * @param mapperSuperClass      Mapper接口父类
     * @param entitySupperClass     实体父类
     * @param cacheClass            缓存实现类
     */
    public StrategyConfig config(List<String> includes, List<String> exludes, List<IFill> fills, Class<?> controllerSuperClass, Class<?> serviceSuperClass, Class<?> serviceImplSuperClass, Class<?> mapperSuperClass, Class<?> entitySupperClass, Class<? extends Cache> cacheClass) {

        LikeTable like = null;
        boolean sqlFilter = true;
        if (CollectionUtils.isEmpty(includes) && CollectionUtils.isEmpty(exludes) && CollectionUtils.isNotEmpty(prefixs)) {
            like = new LikeTable(prefixs.get(0));
            sqlFilter = false;
        }
        //1.装配策略配置
        StrategyConfig config = SqlGenerator.strategy(false, false, sqlFilter, false, like, null, includes, exludes, prefixs, null, null, null);
        //2.实体默认配置
        SqlGenerator.entity(config, null, entitySupperClass, null, false, false, true, true, false, true, true, "version", "version", "delete_flag", "deleteFlag", NamingStrategy.underline_to_camel, NamingStrategy.underline_to_camel, null, null, fills, IdType.AUTO, null, "%s");
        //3.控制层默认配置
        SqlGenerator.controller(config, controllerSuperClass, null, false, true, null, "%sController");
        //4.业务层默认配置
        SqlGenerator.service(config, serviceSuperClass, null, serviceImplSuperClass, null, null, null, "I%sService", "%sServiceImpl");
        //5.映射层模式配置
        SqlGenerator.mapper(config, mapperSuperClass, null, true, true, false, cacheClass, null, null, "%sMapper", "%s");

        return config;
    }
}
