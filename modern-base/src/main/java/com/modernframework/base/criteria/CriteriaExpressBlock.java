package com.modernframework.base.criteria;

import com.modernframework.core.utils.CollectionUtils;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;


/**
 * CriteriaExpressBlock
 * <pre>
 * [a = 1] -> [and] -> [b = 1] -> [and] -> [(] -> [c = 2] -> [or] -> [d = 3] -> [)]
 *
 * </pre>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class CriteriaExpressBlock implements CriteriaExpress {

    @Getter
    private final LinkedList<CriteriaExpress> nodes = new LinkedList<>();

    private int cursor = 0;

    /**
     * 获取下一个节点组合
     * <pre>
     *     [a = 1] -> [and] -> [b = 1] -> [and] -> [(] -> [c = 2] -> [or] -> [d = 3] -> [)]
     *
     *     next -> [[null], [a = 1]]
     *     next -> [[and], [b = 1]]
     *     next -> [[and], [(] -> [c = 2] -> [or] -> [d = 3] -> [)]]
     *
     * </pre>
     */
    public CriteriaExpress[] nextBlock() {
        CriteriaExpress link;
        CriteriaExpress express;
        // 为了保证数据结构对其，头节点需要做特殊的获取方式
        if (cursor <= 0) {
            // 头结点，默认都是 and 连接
            // 头节点的情况有：[a = 1] (CriteriaParamItem)、 [(] (EmbeddedLink)；
            // 如果是 EmbeddedLink 触发内嵌
            // 如果是 CriteriaParamItem 非内嵌
            CriteriaExpress first = safeNext();
            if (first instanceof EmbeddedLink) {
                // [(] (EmbeddedLink) 的情况
                link = R_AND;
                express = first;
            } else {
                // [a = 1] (CriteriaParamItem) 的情况
                link = R_AND;
                express = first;
            }
        } else {
            // 非头节点
            link = safeNext();
            express = safeNext();
        }

        // 任何一个是 null，则返回 null
        if (link == null || express == null) {
            return null;
        }

        if (express instanceof CriteriaParamItem) {
            // 正常表达式形式直接返回
            return new CriteriaExpress[]{link, express};
        } else if (express instanceof EmbeddedLink) {
            // 内嵌
            CriteriaExpressBlock embeddedCriteriaExpressBlock = new CriteriaExpressBlock();
            int embeddedStartCount = 1;
            CriteriaExpress pos = null;
            while (embeddedStartCount > 0 && (pos = safeNext()) != null) {
                if (EMBEDDED_START.equals(pos)) {
                    ++embeddedStartCount;
                    embeddedCriteriaExpressBlock.appendNode(pos);
                } else if (EMBEDDED_END.equals(pos)) {
                    if (embeddedStartCount > 1) {
                        embeddedCriteriaExpressBlock.appendNode(pos);
                    }
                    --embeddedStartCount;
                } else {
                    embeddedCriteriaExpressBlock.appendNode(pos);
                }
            }
            return new CriteriaExpress[]{link, embeddedCriteriaExpressBlock};
        } else {
            // 其他 - null
            return null;
        }
    }

    public void resetCursor() {
        this.cursor = 0;
    }

    private CriteriaExpress safeNext() {
        if (cursor >= nodes.size()) {
            return null;
        }
        return nodes.get(cursor++);
    }

    /**
     * And 节点
     *
     * @param node
     */
    public void and(CriteriaExpress node) {
        if (!isEmpty()) {
            this.appendNode(R_AND);
        }
        this.appendNode(node);
    }

    /**
     * Or 节点
     *
     * @param node
     */
    public void or(CriteriaExpress node) {
        if (!isEmpty()) {
            this.appendNode(R_OR);
        }
        this.appendNode(node);
    }

    /**
     * And 内嵌节点
     *
     * @param embeddedNodeBlock
     */
    public void andEmbedded(CriteriaExpressBlock embeddedNodeBlock) {
        this.appendNode(R_AND)
                .appendNode(EMBEDDED_START)
                .appendAllNodes(embeddedNodeBlock.getNodes())
                .appendNode(EMBEDDED_END);
    }

    /**
     * And 内嵌节点
     *
     * @param embeddedNodes
     */
    public void andEmbedded(List<CriteriaExpress> embeddedNodes) {
        this.appendNode(R_AND)
                .appendNode(EMBEDDED_START)
                .appendAllNodes(embeddedNodes)
                .appendNode(EMBEDDED_END);
    }


    /**
     * Or 内嵌节点
     *
     * @param embeddedNodes
     */
    public void orEmbedded(List<CriteriaExpress> embeddedNodes) {
        this.appendNode(R_OR)
                .appendNode(EMBEDDED_START)
                .appendAllNodes(embeddedNodes)
                .appendNode(EMBEDDED_END);
    }

    /**
     * And 内嵌节点
     *
     * @param embeddedNodeBlock
     */
    public void orEmbedded(CriteriaExpressBlock embeddedNodeBlock) {
        this.appendNode(R_OR)
                .appendNode(EMBEDDED_START)
                .appendAllNodes(embeddedNodeBlock.getNodes())
                .appendNode(EMBEDDED_END);
    }

    /**
     * 用括号包裹自己 <br/>
     * a = 1 ==> ( a = 1 )
     */
    public void embeddedSelf() {
        this.appendFirstNode(EMBEDDED_START).appendNode(EMBEDDED_END);
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(nodes);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        nodes.forEach(sb::append);
        return sb.toString();
    }

    // ---------------------- private --------------------------

    /**
     * 前面插入节点
     */
    private CriteriaExpressBlock appendFirstNode(CriteriaExpress express) {
        this.nodes.addFirst(express);
        return this;
    }

    /**
     * 后面插入节点
     */
    public CriteriaExpressBlock appendNode(CriteriaExpress express) {
        this.nodes.add(express);
        return this;
    }

    private CriteriaExpressBlock appendAllNodes(List<CriteriaExpress> expresses) {
        this.nodes.addAll(expresses);
        return this;
    }

}
