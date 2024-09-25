/**
 * m
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
module modern.orm.mp {
    requires com.baomidou.mybatis.plus.annotation;
    requires com.baomidou.mybatis.plus.boot.starter;
    requires com.baomidou.mybatis.plus.core;
    requires com.baomidou.mybatis.plus.extension;
    requires java.sql;
    requires json.lib;
    requires jsqlparser;
    requires static lombok;
    requires modern.base;
    requires modern.core;
    requires modern.orm.api;
    requires mybatis.plus.generator;
    requires org.mybatis;
    requires org.slf4j;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.expression;

    exports com.modern.orm.mp;
    exports com.modern.orm.mp.criteria;
    exports com.modern.orm.mp.generator;
    exports com.modern.orm.mp.mapper;
    exports com.modern.orm.mp.service;
    exports com.modern.orm.mp.utils;

    opens com.modern.orm.mp.config to spring.core;
}
