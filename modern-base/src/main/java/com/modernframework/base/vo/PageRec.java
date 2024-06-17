package com.modernframework.base.vo;

import com.modernframework.base.criteria.GrateParam;
import com.modernframework.core.utils.CollectionUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 自定义分页组件
 *
 * @author zj
 *@since 1.0.0
 */
@Getter
@NoArgsConstructor
public class PageRec<T> {
    /**
     * 每页显示条数，默认 10
     */
    private int pageSize = 10;
    /**
     * 当前页
     */
    private int pageNumber = 1;
    /**
     * 总条数
     */
    private int total = 0;
    /**
     * 总页数
     */
    protected int pages = 1;

    /**
     * 数据
     */
    private List<T> records;

    /**
     * 空数据
     */
    public static final PageRec<Object> EMPTY_PAGE = new PageRec<>(1, 10, Collections.emptyList(), 0);

    public PageRec(int pageNumber, int pageSize, int total) {
        this(pageNumber, pageSize, Collections.emptyList(), total);
    }

    public PageRec(GrateParam<T> param, List<T> records, int total) {
        this(param.getPageNumber(), param.getPageSize(), records, total);
    }

    public PageRec(int pageNumber, int pageSize, List<T> records, int total) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.records = records;
        this.total = total;
        this.pages = total <= pageSize ? 1 : (total + (pageSize - 1)) / pageSize;
    }

    /**
     * 生成MP的分页组件，用于查询，只传递页数和单页大小
     *
     * @return Page<T>
     */
    public PageRec<T> page() {
        return this;
    }


    /**
     * 设置分页单页记录大小
     *
     * @param pageSize 分页大小
     * @return PageRecord<T>
     */
    public PageRec<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 设置当前页
     *
     * @param pageNumber 分页页数
     * @return PageRecord<T>
     */
    public PageRec<T> setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    /**
     * 设置数据
     *
     * @return PageRecord<T>
     */
    public PageRec<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    /**
     * 创建一个空的分页数据
     */
    public static  <R> PageRec<R> emptyPage(int pageNumber, int pageSize) {
        return new PageRec<>(pageNumber, pageSize, Collections.emptyList(), 0);
    }

    /**
     * 创建一个空的分页数据
     */
    public static  <R> PageRec<R> emptyPage(GrateParam<R> param) {
        return new PageRec<>(param.getPageNumber(), param.getPageSize(), Collections.emptyList(), 0);
    }

    /**
     * PageRecord 的泛型转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 PageRecord
     */
    public <R> PageRec<R> convert(Function<? super T, ? extends R> mapper) {
        PageRec<R> result = new PageRec<>(this.pageNumber, this.pageSize, this.total);
        return result.setRecords(this.getRecords().stream().map(mapper).collect(toList()));
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(this.getRecords());
    }

    public int size() {
        return getRecords().size();
    }

    public T get(int index) {
        return getRecords().get(index);
    }
}
