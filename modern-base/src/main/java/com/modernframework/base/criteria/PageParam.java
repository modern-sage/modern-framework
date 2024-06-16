package com.modernframework.base.criteria;

import lombok.Data;

/**
 * 分页查询参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Data
public class PageParam {

    /**
     * 每页显示条数，默认 10
     */
    private int pageSize = 10;
    /**
     * 当前页
     */
    private int pageNumber = 1;

    public PageParam() {
    }

    public PageParam(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 开始位置
     * <pre>
     * 页码：1，每页10 =》 [0, 10]
     * 页码：2，每页10 =》 [10, 20]
     * ……
     * </pre>
     *
     * @return int
     */
    public int getStartPosition() {
        return ((Math.max(pageNumber, 1)) - 1) * Math.max(pageSize, 0);
    }

    /**
     * @return 结束位置
     */
    public int getEndPosition() {
        return getStartPosition() + Math.max(pageSize, 0);
    }
}
