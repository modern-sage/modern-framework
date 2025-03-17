package com.modernframework.base.criteria;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modernframework.base.BaseConstant;
import com.modernframework.base.criteria.between.BetweenParam;
import com.modernframework.base.criteria.type.ConditionType;
import com.modernframework.base.criteria.type.FuncType;
import com.modernframework.core.func.SerialFunction;
import com.modernframework.core.lang.Asserts;
import com.modernframework.core.utils.LambdaUtils;
import com.modernframework.core.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.modernframework.base.BaseConstant.ATTR_DELETE_FLAG;
import static com.modernframework.base.BaseConstant.NO;
import static com.modernframework.base.criteria.CriteriaExpress.R_AND;
import static com.modernframework.base.criteria.CriteriaExpress.R_OR;

/**
 * 好用的查询参数
 *
 * @param <T>
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
public class GrateParam<T> implements Serializable {

    /**
     * 分页条件
     */
    protected PageParam pageParam = new PageParam();

    /**
     * 查询条件，数组形式
     */
    @JsonIgnore
    protected CriteriaExpressBlock ceb = new CriteriaExpressBlock();

    /**
     * 限制的查询字段，为null 或为空则为查询全部
     */
    protected Set<String> includeAttributes = new LinkedHashSet<>();

    /**
     * 排除字段
     */
    protected List<String> excludesAttributes = new ArrayList<>();

    /**
     * 强制查询全部字段，当开启的时候，会强制查询所有的字段
     * {@link GrateParam##excludesAttributes}会处于失效状态，为空
     */
    protected boolean queryAllForce = false;

    /**
     * 排序字段
     */
    protected List<OrderBy> orderBys = new ArrayList<>();

    /**
     * groupBy字段
     */
    protected List<String> groupBys = new ArrayList<>();

    /**
     * 默认是否包含一个排序方式
     */
    @JsonIgnore
    protected boolean defaultOrderBy = true;

    public GrateParam() {
    }

    public GrateParam(boolean logic) {
        this();
        this.eq(logic, ATTR_DELETE_FLAG, NO);
    }

    // ------------------------ 分页条件 -------------------------

    /**
     * 当前页
     *
     * @return int
     */
    public int getPageNumber() {
        return pageParam.getPageNumber();
    }

    public GrateParam<T> setPageNumber(int pageNumber) {
        this.pageParam.setPageNumber(pageNumber);
        return this;
    }

    /**
     * 分页数量
     *
     * @return int
     */
    public int getPageSize() {
        return pageParam.getPageSize();
    }

    public GrateParam<T> setPageSize(int pageSize) {
        this.pageParam.setPageSize(pageSize);
        return this;
    }

    /**
     * 排序信息
     *
     * @return List<OrderBy>
     */
    public List<OrderBy> getOrderBys() {
        return orderBys;
    }


    // ------------------------ 指定字段 -------------------------

    public GrateParam<T> setQueryAllForce(boolean queryAllForce) {
        this.queryAllForce = queryAllForce;
        return this;
    }


    /**
     * 选择属性
     *
     * @param attributes 字段边长数组
     * @return QueryParam<T>
     */
    public GrateParam<T> select(Collection<String> attributes) {
        this.includeAttributes.addAll(attributes);
        return this;
    }

    /**
     * 选择属性
     *
     * @param attributes 字段边长数组
     * @return QueryParam<T>
     */
    public GrateParam<T> select(String... attributes) {
        this.includeAttributes.addAll(Arrays.asList(attributes));
        return this;
    }

    /**
     * 选择属性
     */
    public GrateParam<T> select(SerialFunction<T, ?>... functions) {
        this.includeAttributes.addAll(Arrays.stream(functions).map(this::getAttributeNameFromMethod).collect(Collectors.toList()));
        return this;
    }

    /**
     * 选择属性
     */
    public <R> GrateParam<T> select(Class<R> clazz, SerialFunction<R, ?>... functions) {
        this.includeAttributes.addAll(Arrays.stream(functions).map(this::getAttributeNameFromMethod).collect(Collectors.toList()));
        return this;
    }

    /**
     * 排序
     */
    public GrateParam<T> setOrderBy(String attribute, boolean isAsc) {
        if(this.getOrderBys() == null) {
            this.setOrderBys(new ArrayList<>());
        }
        OrderBy orderBy = new OrderBy();
        orderBy.setAttribute(attribute);
        orderBy.setAsc(isAsc? BaseConstant.ASC : BaseConstant.DESC);
        this.getOrderBys().add(orderBy);
        return this;
    }

    /**
     * AND 相等查询参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> eq(String attribute, Object value) {
        return append(true, R_AND, ConditionType.EQ, attribute, value);
    }

    /**
     * AND 相等查询参数
     *
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> eq(SerialFunction<T, ?> attrFun, Object value) {
        return append(true, R_AND, ConditionType.EQ, attrFun, value);
    }

    /**
     * AND 相等查询参数
     *
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> eq(Class<R> c, SerialFunction<R, ?> attrFun, Object value) {
        return append(true, c, R_AND, ConditionType.EQ, attrFun, value);
    }

    /**
     * AND 相等查询参数
     *
     * @param ifTrue    是否成立
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> eq(boolean ifTrue, String attribute, Object value) {
        return append(ifTrue, R_AND, ConditionType.EQ, attribute, value);
    }

    /**
     * AND 相等查询参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> eq(boolean ifTrue, SerialFunction<T, ?> attrFun, Object value) {
        return append(ifTrue, R_AND, ConditionType.EQ, attrFun, value);
    }

    /**
     * AND 相等查询参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> eq(boolean ifTrue, Class<R> c, SerialFunction<R, ?> attrFun, Object value) {
        return append(ifTrue, c, R_AND, ConditionType.EQ, attrFun, value);
    }


    /**
     * OR 相等查询参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orEq(String attribute, Object value) {
        return append(true, R_OR, ConditionType.EQ, attribute, value);
    }

    /**
     * OR 相等查询参数
     *
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orEq(SerialFunction<T, ?> attrFun, Object value) {
        return append(true, R_OR, ConditionType.EQ, attrFun, value);
    }

    /**
     * OR 相等查询参数
     *
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orEq(Class<R> c, SerialFunction<R, ?> attrFun, Object value) {
        return append(true, c, R_OR, ConditionType.EQ, attrFun, value);
    }

    /**
     * OR 相等查询参数
     *
     * @param ifTrue    是否成立
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orEq(boolean ifTrue, String attribute, Object value) {
        return append(ifTrue, R_OR, ConditionType.EQ, attribute, value);
    }

    /**
     * OR 相等查询参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orEq(boolean ifTrue, SerialFunction<T, ?> attrFun, Object value) {
        return append(ifTrue, R_OR, ConditionType.EQ, attrFun, value);
    }

    /**
     * OR 相等查询参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orEq(boolean ifTrue, Class<R> c, SerialFunction<R, ?> attrFun, Object value) {
        return append(ifTrue, c, R_OR, ConditionType.EQ, attrFun, value);
    }

    /**
     * AND 不等参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> notEq(String attribute, Object value) {
        return append(true, R_AND, ConditionType.NOT_EQUAL, attribute, value);
    }

    /**
     * AND 不等参数
     *
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> notEq(SerialFunction<T, ?> attrFun, Object value) {
        return append(true, R_AND, ConditionType.NOT_EQUAL, attrFun, value);
    }

    /**
     * AND 不等参数
     *
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> notEq(Class<R> c, SerialFunction<R, ?> attrFun, Object value) {
        return append(true, c, R_AND, ConditionType.NOT_EQUAL, attrFun, value);
    }

    /**
     * AND 不等参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> notEq(boolean ifTrue, String attribute, Object value) {
        return append(ifTrue, R_AND, ConditionType.NOT_EQUAL, attribute, value);
    }

    /**
     * AND 不等参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> notEq(boolean ifTrue, SerialFunction<T, ?> attrFun, Object value) {
        return append(ifTrue, R_AND, ConditionType.NOT_EQUAL, attrFun, value);
    }

    /**
     * AND 不等参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> notEq(boolean ifTrue, Class<R> c, SerialFunction<R, ?> attrFun, Object value) {
        return append(ifTrue, c, R_AND, ConditionType.NOT_EQUAL, attrFun, value);
    }

    /**
     * OR 不等参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orNotEq(String attribute, Object value) {
        return append(true, R_OR, ConditionType.NOT_EQUAL, attribute, value);
    }


    /**
     * OR 不等参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orNotEq(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.NOT_EQUAL, getMethod, value);
    }

    /**
     * OR 不等参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orNotEq(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_OR, ConditionType.NOT_EQUAL, getMethod, value);
    }

    /**
     * AND 模糊参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> like(String attribute, Object value) {
        return append(true, R_AND, ConditionType.LIKE, attribute, value);
    }

    /**
     * AND 模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> like(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.LIKE, getMethod, value);
    }

    /**
     * AND 模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> like(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_AND, ConditionType.LIKE, getMethod, value);
    }

    /**
     * OR 模糊参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLike(String attribute, Object value) {
        return append(true, R_OR, ConditionType.LIKE, attribute, value);
    }

    /**
     * OR 模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLike(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.LIKE, getMethod, value);
    }

    /**
     * OR 模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orLike(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_OR, ConditionType.LIKE, getMethod, value);
    }

    /**
     * AND 左模糊参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> likeLeft(String attribute, Object value) {
        return append(true, R_AND, ConditionType.LIKE_LEFT, attribute, value);
    }

    /**
     * AND 左模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> likeLeft(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.LIKE_LEFT, getMethod, value);
    }

    /**
     * AND 左模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> likeLeft(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_AND, ConditionType.LIKE_LEFT, getMethod, value);
    }

    /**
     * OR 左模糊参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLikeLeft(String attribute, Object value) {
        return append(true, R_OR, ConditionType.LIKE_LEFT, attribute, value);
    }

    /**
     * OR 左模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLikeLeft(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.LIKE_LEFT, getMethod, value);
    }

    /**
     * OR 左模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orLikeLeft(Class<R> c,SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_OR, ConditionType.LIKE_LEFT, getMethod, value);
    }


    /**
     * AND 右模糊参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> likeRight(String attribute, Object value) {
        return append(true, R_AND, ConditionType.LIKE_RIGHT, attribute, value);
    }

    /**
     * OR 右模糊参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLikeRight(String attribute, Object value) {
        return append(true, R_OR, ConditionType.LIKE_RIGHT, attribute, value);
    }

    /**
     * AND 右模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> likeRight(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.LIKE_RIGHT, getMethod, value);
    }

    /**
     * AND 右模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> likeRight(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_AND, ConditionType.LIKE_RIGHT, getMethod, value);
    }

    /**
     * OR 右模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLikeRight(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.LIKE_RIGHT, getMethod, value);
    }

    /**
     * OR 右模糊参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orLikeRight(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_OR, ConditionType.LIKE_RIGHT, getMethod, value);
    }

    /**
     * AND 大于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> gt(String attribute, Object value) {
        return append(true, R_AND, ConditionType.GT, attribute, value);
    }

    /**
     * AND 大于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> gt(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.GT, getMethod, value);
    }

    /**
     * OR 大于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orGt(String attribute, Object value) {
        return append(true, R_OR, ConditionType.GT, attribute, value);
    }


    /**
     * OR 大于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orGt(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.GT, getMethod, value);
    }

    /**
     * OR 大于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> orGt(Class<R> c, SerialFunction<R, ?> getMethod, Object value) {
        return append(true, c, R_OR, ConditionType.GT, getMethod, value);
    }


    /**
     * AND 小于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> lt(String attribute, Object value) {
        return append(true, R_AND, ConditionType.LT, attribute, value);
    }

    /**
     * OR 小于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLt(String attribute, Object value) {
        return append(true, R_OR, ConditionType.LT, attribute, value);
    }

    /**
     * AND 小于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> lt(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.LT, getMethod, value);
    }

    /**
     * OR 小于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLt(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.LT, getMethod, value);
    }


    /**
     * AND 小于等于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> le(String attribute, Object value) {
        return append(true, R_AND, ConditionType.LE, attribute, value);
    }

    /**
     * OR 小于等于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLe(String attribute, Object value) {
        return append(true, R_OR, ConditionType.LE, attribute, value);
    }

    /**
     * AND 小于等于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> le(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.LE, getMethod, value);
    }

    /**
     * OR 小于等于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orLe(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.LE, getMethod, value);
    }

    /**
     * AND 大于等于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> ge(String attribute, Object value) {
        return append(true, R_AND, ConditionType.GE, attribute, value);
    }

    /**
     * OR 大于等于参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orGe(String attribute, Object value) {
        return append(true, R_OR, ConditionType.GE, attribute, value);
    }

    /**
     * AND 大于等于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> ge(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_AND, ConditionType.GE, getMethod, value);
    }

    /**
     * OR 大于等于参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orGe(SerialFunction<T, ?> getMethod, Object value) {
        return append(true, R_OR, ConditionType.GE, getMethod, value);
    }

    /**
     * AND 范围参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> in(String attribute, Collection<?> value) {
        return append(true, R_AND, ConditionType.IN, attribute, value);
    }

    /**
     * OR 范围参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orIn(String attribute, Collection<?> value) {
        return append(true, R_OR, ConditionType.IN, attribute, value);
    }

    /**
     * AND 范围参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> in(SerialFunction<T, ?> getMethod, Collection<?> value) {
        return append(true, R_AND, ConditionType.IN, getMethod, value);
    }

    /**
     * AND 范围参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> in(Class<R> c, SerialFunction<R, ?> getMethod, Collection<?> value) {
        return append(true, c, R_AND, ConditionType.IN, getMethod, value);
    }


    /**
     * OR 范围参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orIn(SerialFunction<T, ?> getMethod, Collection<?> value) {
        return append(true, R_OR, ConditionType.IN, getMethod, value);
    }

    /**
     * AND 范围参数
     *
     * @param getMethod     参数Get方法函数
     * @param valueSupplier 参数值函数
     * @return QueryParam<T>
     */
    public GrateParam<T> in(SerialFunction<T, ?> getMethod, Supplier<Collection<?>> valueSupplier) {
        return append(true, R_AND, ConditionType.IN, getMethod, valueSupplier.get());
    }

    /**
     * AND 范围参数
     *
     * @param getMethod     参数Get方法函数
     * @param valueSupplier 参数值函数
     * @return QueryParam<T>
     */
    public GrateParam<T> orIn(SerialFunction<T, ?> getMethod, Supplier<Collection<?>> valueSupplier) {
        return append(true, R_OR, ConditionType.IN, getMethod, valueSupplier.get());
    }

    /**
     * AND 范围反向参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> notIn(String attribute, Collection<?> value) {
        return append(true, R_AND, ConditionType.NOT_IN, attribute, value);
    }

    /**
     * OR 范围反向参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orNotIn(String attribute, Collection<?> value) {
        return append(true, R_OR, ConditionType.NOT_IN, attribute, value);
    }

    /**
     * AND 范围反向参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> notIn(SerialFunction<T, ?> getMethod, Collection<?> value) {
        return append(true, R_AND, ConditionType.NOT_IN, getMethod, value);
    }

    /**
     * OR 范围反向参数
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orNotIn(SerialFunction<T, ?> getMethod, Collection<?> value) {
        return append(true, R_OR, ConditionType.NOT_IN, getMethod, value);
    }

    /**
     * AND 范围反向参数
     *
     * @param getMethod     参数Get方法函数
     * @param valueSupplier 参数值函数
     * @return QueryParam<T>
     */
    public GrateParam<T> notIn(SerialFunction<T, ?> getMethod, Supplier<Collection<?>> valueSupplier) {
        return append(true, R_AND, ConditionType.NOT_IN, getMethod, valueSupplier.get());
    }

    /**
     * OR 范围反向参数
     *
     * @param getMethod     参数Get方法函数
     * @param valueSupplier 参数值函数
     * @return QueryParam<T>
     */
    public GrateParam<T> orNotIn(SerialFunction<T, ?> getMethod, Supplier<Collection<?>> valueSupplier) {
        return append(true, R_OR, ConditionType.NOT_IN, getMethod, valueSupplier.get());
    }

    /**
     * AND ${attribute} ISNULL
     *
     * @param attribute 参数名
     * @return QueryParam<T>
     */
    public GrateParam<T> isNull(String attribute) {
        return append(true, R_AND, ConditionType.IS_NULL, attribute, null);
    }

    /**
     * OR ${attribute} ISNULL
     *
     * @param attribute 参数名
     * @return QueryParam<T>
     */
    public GrateParam<T> orIsNull(String attribute) {
        return append(true, R_OR, ConditionType.IS_NULL, attribute, null);
    }

    /**
     * AND ${attribute} ISNULL
     *
     * @param getMethod 参数Get方法函数
     * @return QueryParam<T>
     */
    public GrateParam<T> isNull(SerialFunction<T, ?> getMethod) {
        return append(true, R_AND, ConditionType.IS_NULL, getMethod, null);
    }

    /**
     * OR ${attribute} ISNULL
     *
     * @param getMethod 参数Get方法函数
     * @return QueryParam<T>
     */
    public GrateParam<T> orIsNull(SerialFunction<T, ?> getMethod) {
        return append(true, R_OR, ConditionType.IS_NULL, getMethod, null);
    }


    /**
     * AND ${attribute} IS NOT NULL
     *
     * @param attribute 参数名
     * @return QueryParam<T>
     */
    public GrateParam<T> isNotNull(String attribute) {
        return append(true, R_AND, ConditionType.IS_NOT_NULL, attribute, null);
    }

    /**
     * OR ${attribute} IS NOT NULL
     *
     * @param attribute 参数名
     * @return QueryParam<T>
     */
    public GrateParam<T> orIsNotNull(String attribute) {
        return append(true, R_OR, ConditionType.IS_NOT_NULL, attribute, null);
    }

    /**
     * AND ${attribute} IS NOT NULL
     *
     * @param getMethod 参数Get方法函数
     * @return QueryParam<T>
     */
    public GrateParam<T> isNotNull(SerialFunction<T, ?> getMethod) {
        return append(true, R_AND, ConditionType.IS_NOT_NULL, getMethod, null);
    }

    /**
     * OR ${attribute} IS NOT NULL
     *
     * @param getMethod 参数Get方法函数
     * @return QueryParam<T>
     */
    public GrateParam<T> orIsNotNull(SerialFunction<T, ?> getMethod) {
        return append(true, R_OR, ConditionType.IS_NOT_NULL, getMethod, null);
    }

    /**
     * AND ${attribute} BETWEEN ${loValue} and ${hiValue}
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> between(String attribute, BetweenParam value) {
        return append(true, R_AND, ConditionType.BETWEEN, attribute, value);
    }

    /**
     * OR ${attribute} BETWEEN ${loValue} and ${hiValue}
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orBetween(String attribute, BetweenParam value) {
        return append(true, R_OR, ConditionType.BETWEEN, attribute, value);
    }

    /**
     * AND ${attribute} BETWEEN ${loValue} and ${hiValue}
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> between(SerialFunction<T, ?> getMethod, BetweenParam value) {
        return append(true, R_AND, ConditionType.BETWEEN, getMethod, value);
    }

    /**
     * OR ${attribute} BETWEEN ${loValue} and ${hiValue}
     *
     * @param getMethod 参数Get方法函数
     * @param value     参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> orBetween(SerialFunction<T, ?> getMethod, BetweenParam value) {
        return append(true, R_OR, ConditionType.BETWEEN, getMethod, value);
    }

    // -------------------------- 内嵌 ----------------------------------

    /**
     * and 方式连接內联一组条件
     *
     * @param consumer
     * @return QueryParam
     */
    public GrateParam<T> and(Consumer<GrateParam<T>> consumer) {
        GrateParam<T> embedded = new GrateParam<>();
        consumer.accept(embedded);
        ceb.andEmbedded(embedded.getCeb().getNodes());
        return this;
    }


    /**
     * or方式连接內联一组条件
     *
     * @param consumer
     * @return QueryParam
     */
    public GrateParam<T> or(Consumer<GrateParam<T>> consumer) {
        GrateParam<T> embedded = new GrateParam<>();
        consumer.accept(embedded);
        ceb.orEmbedded(embedded.getCeb().getNodes());
        return this;
    }

    /**
     * 包裹自己
     */
    public GrateParam<T> wrapSelf() {
        ceb.embeddedSelf();
        return this;
    }

    // -------------------------- 排序 ----------------------------------

    /**
     * 添加排序字段
     *
     * @param orders 排序字段
     * @return QueryParam<T>
     */
    public GrateParam<T> orderBy(OrderBy... orders) {
        Asserts.notNull(orders, () -> new RuntimeException("排序字段不能传null值"));
        orderBys.addAll(Arrays.asList(orders));
        return this;
    }

    /**
     * 添加排序字段
     *
     * @param fields 排序字段
     * @return QueryParam<T>
     */
    public GrateParam<T> orderBy(SerialFunction<T, ?>... fields) {
        Arrays.asList(fields).forEach(f -> orderBy(f, BaseConstant.ASC));
        return this;
    }

    /**
     * 添加排序字段
     *
     * @param fields 排序字段
     * @return QueryParam<T>
     */
    public GrateParam<T> orderByDesc(SerialFunction<T, ?>... fields) {
        Arrays.asList(fields).forEach(f -> orderBy(f, BaseConstant.DESC));
        return this;
    }

    /**
     * 添加排序字段
     *
     * @param attribute 字段
     * @param asc       排序方式
     * @return QueryParam<T>
     */
    public GrateParam<T> orderBy(String attribute, int asc) {
        OrderBy orderBy = new OrderBy(attribute, asc);
        orderBys.add(orderBy);
        return this;
    }


    /**
     * 添加排序字段
     *
     * @param field 字段
     * @param asc   排序方式
     * @return QueryParam<T>
     */
    public GrateParam<T> orderBy(SerialFunction<T, ?> field, int asc) {
        OrderBy orderBy = new OrderBy(getAttributeNameFromMethod(field), asc);
        orderBys.add(orderBy);
        return this;
    }

    // -------------------------- 分组 ----------------------------------

    /**
     * 添加排序字段
     *
     * @param groups 分组字段
     * @return QueryParam<T>
     */
    public GrateParam<T> groupBy(String... groups) {
        groupBys.addAll(Arrays.asList(groups));
        return this;
    }

    /**
     * 添加排序字段
     *
     * @param groups 分组字段
     * @return QueryParam<T>
     */
    public GrateParam<T> groupBy(SerialFunction<T, ?>... groups) {
        Asserts.notNull(groups, () -> new RuntimeException("分组字段不能传null值"));
        List<SerialFunction<T, ?>> list = Arrays.asList(groups);
        groupBys.addAll(list.stream().map(this::getAttributeNameFromMethod).collect(Collectors.toList()));
        return this;
    }

    // -------------------------- 函数调用 ----------------------------------

    /**
     * 函数调用
     *
     * @param type  调用类型
     * @param field 字段名
     * @param alias 别名
     * @return SqlFunc 当前对象本身
     */
    public GrateParam<T> func(FuncType type, String field, String alias) {
        if (StringUtils.isBlank(alias)) {
            alias = field;
        }
        Asserts.notNull(type, () -> new RuntimeException("未知函数名称"));
        field = String.format("IFNULL( %s(%s),0) as %s", type.name().toLowerCase(), field, alias);
        this.includeAttributes.add(field);
        return this;
    }

    /**
     * 函数调用
     *
     * @param type  调用类型
     * @param field 字段名
     * @param alias 别名
     * @return SqlFunc 当前对象本身
     */
    public GrateParam<T> func(FuncType type, SerialFunction<T, ?> field, String alias) {
        return func(type, getAttributeNameFromMethod(field), alias);
    }

    // ---------------------  private -----------------------

    /**
     * 添加查询参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> append(boolean ifTrue, Relationship relationship, ConditionType conditionType, SerialFunction<T, ?> attrFun, Object value) {
        return append(ifTrue, relationship, conditionType, getAttributeNameFromMethod(attrFun), value);
    }

    /**
     * 添加查询参数
     *
     * @param ifTrue  是否成立
     * @param attrFun 参数名
     * @param value   参数值
     * @return QueryParam<T>
     */
    public <R> GrateParam<T> append(boolean ifTrue, Class<R> c,Relationship relationship, ConditionType conditionType, SerialFunction<R, ?> attrFun, Object value) {
        return append(ifTrue, relationship, conditionType, getAttributeNameFromMethod(attrFun), value);
    }

    /**
     * 添加查询参数
     *
     * @param ifTrue        是否成立
     * @param attributeName 参数名
     * @param value         参数值
     * @return QueryParam<T>
     */
    public GrateParam<T> append(boolean ifTrue, Relationship relationship, ConditionType conditionType, String attributeName, Object value) {
        if (ifTrue) {
            if (R_AND.equals(relationship)) {
                return andParam(attributeName, value, conditionType);
            } else if (R_OR.equals(relationship)) {
                return orParam(attributeName, value, conditionType);
            } else {
                throw new UnsupportedOperationException("不支持的逻辑连接符");
            }
        }
        return this;
    }

    /**
     * AND 参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    private GrateParam<T> andParam(String attribute, Object value, ConditionType conditionType) {
        ceb.and(new CriteriaParamItem(attribute, value, conditionType.name()));
        return this;
    }

    /**
     * Or 参数
     *
     * @param attribute 参数名
     * @param value     参数值
     * @return QueryParam<T>
     */
    private GrateParam<T> orParam(String attribute, Object value, ConditionType conditionType) {
        ceb.or(new CriteriaParamItem(attribute, value, conditionType.name()));
        return this;
    }

    /**
     * 获取字段名称
     *
     * @param function 字段属性名称
     * @return String
     */
    private <R> String getAttributeNameFromMethod(SerialFunction<R, ?> function) {
        return LambdaUtils.getFieldName(function);
    }
}
