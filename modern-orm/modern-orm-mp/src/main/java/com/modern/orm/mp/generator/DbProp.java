package com.modern.orm.mp.generator;

import lombok.Data;


/**
 * 实体数据库配置参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class DbProp {

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
     * 数据库 schema(部分数据库适用)
     */
    private String schema = "schema";

}
