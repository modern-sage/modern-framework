package com.modernframework.base.service;


import com.modernframework.base.PageRec;
import com.modernframework.base.criteria.GrateParam;
import com.modernframework.base.criteria.type.FuncType;
import com.modernframework.core.func.SerialFunction;
import com.modernframework.core.utils.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 基本Service继承接口
 *
 * @author zj
 *@since 1.0.0
 */
public interface BaseService<T> {

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return T
     */
    T getById(Serializable id);

    /**
     * 保存一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean save(T entity);

    /**
     * 保存（批量）
     *
     * @param entityList 实体对象集合
     */
    boolean saveBatch(Collection<T> entityList);

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    boolean updateById(T entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    boolean removeById(Serializable id);

    /**
     * 批量删除(jdbc批量提交)
     *
     * @param list 主键ID或实体列表(主键ID类型必须与实体类型字段保持一致)
     * @return 删除结果
     */
    boolean removeBatchByIds(Collection<?> list);

    /**
     * 根据查询条件返回查询分页结果
     *
     * @param param 查询条件
     * @return PageRecord<T>
     */
    PageRec<T> page(GrateParam<T> param);

    /**
     * 查询所有
     */
    List<T> list();

    /**
     * 查询所有
     *
     * @param mapping 映射函数
     * @return List<T>
     */
    default <R> List<R> list(Function<T, R> mapping) {
        List<T> list = list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(mapping).collect(Collectors.toList());
    }

    /**
     * 根据查询条件返回查询集合结果
     *
     * @param param 查询条件
     * @return List<T>
     */
    List<T> list(GrateParam<T> param);

    /**
     * 根据查询条件返回查询集合结果
     *
     * @param param   查询条件
     * @param mapping 映射函数
     * @return List<T>
     */
    default <R> List<R> list(GrateParam<T> param, SerialFunction<T, R> mapping) {
        List<T> list = list(param);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(x -> mapping.callWithRuntimeException(x)).collect(Collectors.toList());
    }

    /**
     * 根据`bbId`集合查询`aaId`集合
     *
     * @param bbIds         bbId集合
     * @param bbIdNameFunc  bbId属性名函数
     * @param aaIdValueFunc aaId值函数
     * @return
     */
    default List<Long> listAaIdsByBbIds(List<Long> bbIds, SerialFunction<T, ?> bbIdNameFunc, 
                                        SerialFunction<T, Long> aaIdValueFunc) {
        return listAaIdsByBbIds(bbIds, bbIdNameFunc, aaIdValueFunc, null);
    }

    /**
     * 根据`bbId`集合查询`aaId`集合
     *
     * @param bbIds         bbId集合
     * @param bbIdNameFunc  bbId属性名函数
     * @param aaIdValueFunc aaId值函数
     * @param extraParam    其他的查询配置
     * @return
     */
    default List<Long> listAaIdsByBbIds(List<Long> bbIds, SerialFunction<T, ?> bbIdNameFunc,
                                        SerialFunction<T, Long> aaIdValueFunc,
                                        Consumer<GrateParam<T>> extraParam) {
        if (CollectionUtils.isEmpty(bbIds)) {
            return Collections.emptyList();
        }
        GrateParam<T> where = where();
        where.select(aaIdValueFunc);
        if (extraParam != null) {
            extraParam.accept(where);
        }
        return list(where.in(bbIdNameFunc, bbIds), aaIdValueFunc);
    }


    /**
     * 根据查询条件返回查询单条记录
     *
     * @param param 查询条件
     * @return T
     */
    default T one(GrateParam<T> param) {
        PageRec<T> page = page(param);
        if (page.isEmpty()) {
            return null;
        } else {
            return page.getRecords().get(0);
        }
    }

    /**
     * 根据条件返回查询总条数
     *
     * @param param 查询条件
     * @return Long
     */
    Long count(GrateParam<T> param);

    /**
     * 根据条件更新数据
     *
     * @param entity 更新对象的属性
     * @param param  更新对象的条件
     * @return boolean
     */
    boolean update(T entity, GrateParam<T> param);

    /**
     * 根据条件删除记录
     *
     * @param param 删除条件
     * @return boolean
     */
    boolean remove(GrateParam<T> param);

    /**
     * 根据条件设置指定字段值为null，使用param中的fields字段集合做为更新字段集合
     *
     * @param param 置NULL参数
     * @return boolean
     */
    boolean updateFieldNull(GrateParam<T> param);

    /**
     * 根据条件物理删除
     *
     * @param param 删除条件
     * @return boolean
     */
    boolean deletePhy(GrateParam<T> param);

    /**
     * 设置指定类型分页查询
     *
     * @param param       查询条件
     * @param targetClass 指定类型
     * @param <V>         返回值类型
     * @return PageRec<V>
     */
    <V> PageRec<V> page(GrateParam<T> param, Class<V> targetClass);

    /**
     * 设置指定类型查询列表
     *
     * @param param       查询条件
     * @param targetClass 指定类型
     * @param <V>         返回值类型
     * @return List<V>
     */
    <V> List<V> list(GrateParam<T> param, Class<V> targetClass);

    /**
     * 根据id集合查询
     *
     * @param ids 查询主键集合
     * @return List<V>
     */
    List<T> listByIds(Collection<? extends Serializable> ids);

    /**
     * 设置指定类型查询第一条记录
     *
     * @param param       查询条件
     * @param targetClass 指定类型
     * @param <V>         返回值类型
     * @return V
     */
    default <V> V one(GrateParam<T> param, Class<V> targetClass) {
        PageRec<V> page = page(param, targetClass);
        if (page.isEmpty()) {
            return null;
        } else {
            return page.getRecords().get(0);
        }
    }

    /**
     * 表示条件集合，返回泛型空参数实例，param方法别名
     *
     * @return QueryParam<T>
     */
    default GrateParam<T> where() {
        return new GrateParam<>();
    }

    /**
     * 内置函数
     *
     * @param resType 实体类型
     * @param type    内置函数类型
     * @param param   内置函数类型
     * @param <V>     返回值类型
     * @return List<V>
     */
    <V> List<V> func(Class<V> resType, FuncType type, GrateParam<T> param);

    /**
     * 内置函数,返回一条记录
     *
     * @param resType 实体类型
     * @param type    内置函数类型
     * @param param   内置函数类型
     * @param <V>     返回值类型
     * @return List<V>
     */
    default <V> V funcOne(GrateParam<T> param, FuncType type, Class<V> resType) {
        List<V> list = func(resType, type, param);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
