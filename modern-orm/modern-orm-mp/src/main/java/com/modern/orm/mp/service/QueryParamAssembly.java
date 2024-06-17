package com.modern.orm.mp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.orm.mp.criteria.CombinationCriteriaTranslate;
import com.modernframework.base.criteria.*;
import com.modernframework.base.exception.BizException;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.modernframework.orm.DataOrmConstant.COLUMN_ID;


/**
 * 自定义查询参数包装器
 *
 * @param <T>
 * @author zj
 */
public class QueryParamAssembly<T> {
    private final CombinationCriteriaTranslate combinationCriteriaTranslate = new CombinationCriteriaTranslate();

    private final GrateParam<T> queryParam;

    /**
     * 选择的查询字段列表
     */
    private List<String> selectColumns;

    public QueryParamAssembly(GrateParam<T> queryParam) {
        this.queryParam = queryParam;
    }

    /**
     * 获取选择查询的字段列表
     *
     * @return List<String>
     */
    public List<String> getSelectColumns() {
        if (CollectionUtils.isEmpty(selectColumns)) {
            if (CollectionUtils.isEmpty(queryParam.getIncludeAttributes())) {
                selectColumns = Collections.emptyList();
            } else {
                selectColumns = queryParam.getIncludeAttributes().stream()
                        .map(StringUtils::camelToUnderline).collect(Collectors.toList());
            }
        }
        return selectColumns;
    }


    /**
     * 生成MP的分页组件，用于查询，只传递页数和单页大小
     *
     * @return Page<T>
     */
    public Page<T> pageParam() {
        return new Page<T>()
                .setSize(this.queryParam.getPageSize())
                .setCurrent(this.queryParam.getPageNumber());
    }

    /**
     * 翻译成MP查询条件
     *
     * @return QueryWrapper<T>
     */
    public QueryWrapper<T> translate() {
        QueryWrapper<T> rootQuery = new QueryWrapper<>();
        CriteriaExpressBlock ceb = this.queryParam.getCeb();
        //1.判断直接查询条件集合是否为空，如果不为空则直接使用查询条件查询

        // 将查询条件链转为 QueryWrapper
        recursionCriteriaExpressBlock(ceb, rootQuery);

        // 设置group by
        rootQuery.groupBy(CollectionUtils.isNotEmpty(this.queryParam.getGroupBys()),
                this.queryParam.getGroupBys().stream().map(StringUtils::camelToUnderline).collect(Collectors.toList()));

        // 根据查询字段值设置查询字段
        if (CollectionUtils.isNotEmpty(this.queryParam.getIncludeAttributes())) {
            final List<String> columns = this.queryParam.getIncludeAttributes().stream()
                    .filter(x -> !this.queryParam.getExcludesAttributes().contains(x))
                    .map(StringUtils::camelToUnderline)
                    .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(columns)) {
                throw new BizException("至少需要有一个查询字段");
            }
            rootQuery.select(columns);
        }

        // 添加排序字段
        List<OrderBy> orderBys = this.queryParam.getOrderBys();
        if (CollectionUtils.isEmpty(orderBys) && this.queryParam.isDefaultOrderBy()) {
            orderBys.add(new OrderBy(COLUMN_ID, Constants.DESC));
        }
        orderBys.forEach(o -> rootQuery.orderBy(o != null && StringUtils.isNotBlank(o.getAttribute()),
                o.isAsc(), StringUtils.camelToUnderline(o.getAttribute())));
        return rootQuery;
    }

    /**
     * 递归组合 QueryWrapper
     *
     * @param ceb
     * @param queryWrapper
     */
    public void recursionCriteriaExpressBlock(CriteriaExpressBlock ceb, QueryWrapper<T> queryWrapper) {
        if (ceb.isEmpty()) {
            return;
        }
        CriteriaExpress[] ces;
        while ((ces = ceb.nextBlock()) != null) {
            Relationship relationship = (Relationship) ces[0];
            CriteriaExpress ce = ces[1];

            // 内嵌情况
            if (ce instanceof CriteriaExpressBlock) {
                queryWrapper.and(w -> recursionCriteriaExpressBlock((CriteriaExpressBlock) ce, w));
            } else {
                // 非内嵌
                combinationCriteriaTranslate.translate(queryWrapper, (CriteriaParamItem) ce, relationship);
            }
        }
    }

}
