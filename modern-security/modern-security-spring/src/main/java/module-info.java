/**
 * m
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
module modern.security.spring {
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires static lombok;
    requires org.apache.tomcat.embed.core;
    requires org.mybatis.spring;
    requires org.slf4j;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.jdbc;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.data.redis;
    requires spring.security.crypto;
    requires spring.security.web;
    requires spring.web;
    requires spring.webmvc;

    requires modern.core;
    requires modern.base;
    requires modern.security.api;
    requires modern.orm.mp;
    requires com.fasterxml.jackson.databind;

    opens com.modern.security.spring.config to spring.core;
    opens com.modern.security.spring.support.controller to spring.core;
    opens com.modern.security.spring.support.entity to com.baomidou.mybatis.plus.core;

    exports com.modern.security.spring;
    exports com.modern.security.spring.filter;
    exports com.modern.security.spring.handler;
    exports com.modern.security.spring.service;
    exports com.modern.security.spring.support.controller;
    exports com.modern.security.spring.support.controller.param;
    exports com.modern.security.spring.support.entity;
    exports com.modern.security.spring.support.mapper;
    exports com.modern.security.spring.support.service;
    exports com.modern.security.spring.support.service.impl;
    exports com.modern.security.spring.utils;
    exports com.modern.security.spring.config;
}
