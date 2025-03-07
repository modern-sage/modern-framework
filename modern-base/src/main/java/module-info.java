/**
 * module
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
module modern.base {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires json.lib;
    requires static lombok;
    requires modern.core;
    requires org.apache.commons.lang3;
    requires transmittable.thread.local;

    exports com.modernframework.base;
    exports com.modernframework.base.constant;
    exports com.modernframework.base.criteria;
    exports com.modernframework.base.criteria.between;
    exports com.modernframework.base.criteria.type;
    exports com.modernframework.base.exception;
    exports com.modernframework.base.security;
    exports com.modernframework.base.security.context;
    exports com.modernframework.base.service;
    exports com.modernframework.base.utils;
    exports com.modernframework.base.vo;

    opens com.modernframework.base.criteria to com.fasterxml.jackson.databind, ezmorph, modern.core;
}
