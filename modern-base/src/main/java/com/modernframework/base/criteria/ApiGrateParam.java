package com.modernframework.base.criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modernframework.core.utils.CollectionUtils;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.modernframework.base.criteria.CriteriaExpress.EMBEDDED_END;
import static com.modernframework.base.criteria.CriteriaExpress.EMBEDDED_START;


/**
 * Api层参数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 *@since 1.0.0
 */
@Data
public class ApiGrateParam implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<CriteriaItem> criteria = new LinkedList<>();

    private PageParam pageParam = new PageParam();

    @JsonIgnore
    public <T> GrateParam<T> toGrateParam() {
        final GrateParam<T> param = new GrateParam<>();
        param.setPageParam(pageParam);
        if(CollectionUtils.isNotEmpty(criteria)) {
            criteria.forEach(c -> {
                if(CollectionUtils.isNotEmpty(c.getEmbedded())) {
                    param.ceb.appendNode(new Relationship(c.getLink()));
                    param.ceb.appendNode(EMBEDDED_START);
                    recursionSetParam(param, c.getEmbedded());
                    param.ceb.appendNode(EMBEDDED_END);
                } else {
                    if(!param.ceb.isEmpty()) {
                        param.ceb.appendNode(new Relationship(c.getLink()));
                    }
                    param.ceb.appendNode(new CriteriaParamItem(c.getAttr(), c.getValue(), c.getCondition()));
                }
            });
        }
        return param;
    }

    private <T> void recursionSetParam(GrateParam<T> param, List<CriteriaItem> embedded) {
        for (int i = 0; i < embedded.size(); i++) {
            CriteriaItem c = embedded.get(i);
            if(CollectionUtils.isNotEmpty(c.getEmbedded())) {
                param.ceb.appendNode(new Relationship(c.getLink()));
                param.ceb.appendNode(EMBEDDED_START);
                recursionSetParam(param, c.getEmbedded());
                param.ceb.appendNode(EMBEDDED_END);
            } else {
                if(!param.ceb.isEmpty() && i > 0) {
                    param.ceb.appendNode(new Relationship(c.getLink()));
                }
                param.ceb.appendNode(new CriteriaParamItem(c.getAttr(), c.getValue(), c.getCondition()));
            }
        }
    }

}
