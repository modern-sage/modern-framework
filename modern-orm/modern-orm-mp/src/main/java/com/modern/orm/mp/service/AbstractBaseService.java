package com.modern.orm.mp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.modern.orm.mp.mapper.MdBaseMapper;
import com.modernframework.base.exception.BizException;
import com.modernframework.base.vo.Convertible;
import com.modernframework.base.vo.PageRec;
import com.modernframework.base.criteria.GrateParam;
import com.modernframework.base.criteria.type.FuncType;
import com.modernframework.base.service.BaseService;
import com.modernframework.core.utils.StringUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通用Service继承接口
 *
 * @param <T>
 * @author zj
 */
public abstract class AbstractBaseService<M extends MdBaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * QueryParam -> QueryWrapper
     */
    protected Function<GrateParam<T>, QueryWrapper<T>> assemblyQuery = p -> new QueryParamAssembly<T>(p).translate();

    protected Function<Page<T>, PageRec<T>> pageWrapper = p -> new PageRecordAssembly<>(p).assemblyPageRecord();

    /**
     * 获取实体对象对应的表名
     *
     * @return String
     */
    protected String tableName() {
        return TableInfoHelper.getTableInfo(getEntityClass()).getTableName();
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return T
     */
    @Override
    public T getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean save(T entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(T entity) {
        return super.removeById(entity);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list) {
        return super.removeBatchByIds(list);
    }

    /**
     * 根据查询条件返回查询分页结果
     *
     * @param param 查询条件
     * @return PageRec<T>
     */
    @Override
    public PageRec<T> page(GrateParam<T> param) {
        QueryParamAssembly<T> queryParamWrapper = new QueryParamAssembly<>(param);
        return pageWrapper.apply(getBaseMapper().selectPage(queryParamWrapper.pageParam(), queryParamWrapper.translate()));
    }

    @Override
    public List<T> list() {
        return super.list();
    }

    /**
     * 根据查询条件返回查询集合结果
     *
     * @param param 查询条件
     * @return List<T>
     */
    @Override
    public List<T> list(GrateParam<T> param) {
        return super.list(assemblyQuery.apply(param));
    }

    /**
     * 根据条件返回查询总条数
     *
     * @param param 查询条件
     * @return Long
     */
    @Override
    public Long count(GrateParam<T> param) {
        return super.count(assemblyQuery.apply(param));
    }

    /**
     * 根据条件更新数据
     *
     * @param entity 更新对象的属性
     * @param param  更新对象的条件
     * @return boolean
     */
    @Override
    public boolean update(T entity, GrateParam<T> param) {
        return super.update(entity, assemblyQuery.apply(param));
    }

    /**
     * 根据条件删除记录
     *
     * @param param 删除条件
     * @return boolean
     */
    @Override
    public boolean remove(GrateParam<T> param) {
        return super.remove(assemblyQuery.apply(param));
    }

    /**
     * 根据条件设置指定字段值为null，使用param中的fields字段集合做为更新字段集合
     * 需要指定
     *
     * @param param 置NULL参数
     * @return boolean
     */
    @Override
    public boolean updateFieldNull(GrateParam<T> param) {
        QueryParamAssembly<T> objectQueryParamAssembly = new QueryParamAssembly<>(param);
        return SqlHelper.retBool(getBaseMapper().updateFieldNUll(tableName(),
                objectQueryParamAssembly.getSelectColumns(), objectQueryParamAssembly.translate()));
    }

    /**
     * 根据条件物理删除
     *
     * @param param 删除条件
     * @return boolean
     */
    @Override
    public boolean deletePhy(GrateParam<T> param) {
        QueryWrapper<T> wrapper = assemblyQuery.apply(param);
        return SqlHelper.retBool(getBaseMapper().deletePhy(tableName(), wrapper));
    }

    /**
     * 设置指定类型分页查询
     *
     * @param param       查询条件
     * @param targetClass 指定类型
     * @param convert
     * @return PageRec<V>
     */
    @Override
    public <V> PageRec<V> page(GrateParam<T> param, Class<V> targetClass, Convertible<T, V> convert) {
        PageRec<T> source = page(param);
        PageRec<V> target = new PageRec<>(source.getPageNumber(), source.getPageSize(), source.getTotal());
        List<V> collect = source.getRecords().stream().map(x -> {
                    try {
                        convert.convert(x);
                    } catch (Throwable e) {
                        log.error("page convert error: ", e);
                        throw new BizException(String.format("page convert error: %s", e.getMessage()));
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .map(x -> (V) x)
                .collect(Collectors.toList());
        target.getRecords().addAll(collect);
        return target;
    }

    /**
     * 设置指定类型查询列表
     *
     * @param param       查询条件
     * @param targetClass 指定类型
     * @return List<V>
     */
    @Override
    public <V> List<V> list(GrateParam<T> param, Class<V> targetClass, Convertible<T, V> convert) {
        List<T> source = list(param);
        List<V> target = source.stream().map(x -> {
                    try {
                        convert.convert(x);
                    } catch (Throwable e) {
                        log.error("page convert error: ", e);
                        throw new BizException(String.format("page convert error: %s", e.getMessage()));
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .map(x -> (V) x)
                .collect(Collectors.toList());
        return target;
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    /**
     * 内置函数
     *
     * @param resType 实体类型
     * @param type    内置函数类型
     * @param param   内置函数类型
     * @return List<V>
     */
    @Override
    public <V> List<V> func(Class<V> resType, FuncType type, GrateParam<T> param) {
        return null;
        // todo
    }

}
