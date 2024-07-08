package com.modern.orm.mp.utils;


import com.modern.orm.mp.BaseBizPo;
import com.modern.orm.mp.IdPo;
import com.modern.orm.mp.LinkPo;
import com.modernframework.core.utils.MapUtils;
import com.modernframework.orm.PoType;

import java.util.Map;

/**
 * RepoMpUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public abstract class RepoMpUtils {

    /**
     * 维护普通、关联、树结构下对应的Entity、Mapper、Service、ServiceImpl
     */
    private final static Map<PoType, Class<? extends IdPo>> BASE_PO_MAP;

    static {
        BASE_PO_MAP = MapUtils.of(PoType.NORMAL, BaseBizPo.class, PoType.LINK, LinkPo.class);
    }

    /**
     * 获取实际的 Po 类
     *
     * @param poType      PO类型
     * @return Class<? extends IdPo>
     */
    public static Class<? extends IdPo> getPoClass(PoType poType) {
        return BASE_PO_MAP.get(poType);
    }

}
