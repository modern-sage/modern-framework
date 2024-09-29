package com.modernframework.base;

import com.modernframework.core.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface BaseDict<K, V> {

    Map<String, Map<Object, BaseDict<?,?>>> dictInMemory = new HashMap<>();

    /**
     * 字典名称
     *
     * @return String
     */
    String getName();

    /**
     * 字典项关键字
     *
     * @return K
     */
    K getItemKey();

    /**
     * 字典项显示文本
     *
     * @return String
     */
    String getItemText();

    /**
     * 字典项对象
     *
     * @return String
     */
    V getItem();

    /**
     * 排序
     */
    default int getOrder() {
        return 0;
    }

    @Data
    @NoArgsConstructor
    class DictObj implements BaseDict<Object, Object> {
        private String name;
        private Object itemKey;
        private String itemText;
        private Object item;
        public DictObj(BaseDict<?, ?> dict) {
            this.name = dict.getName();
            this.itemKey = dict.getItemKey();
            this.itemText = dict.getItemText();
            this.item = dict.getItem();
        }
    }

    /**
     * 注册自己
     */
    default void register() {
        dictInMemory.computeIfAbsent(getName(), (k) -> new HashMap<>())
                .put(getItemKey(), new DictObj(this));
    }

    static Collection<BaseDict<?,?>> getDictList() {
       return dictInMemory.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toList());
    }

    static Collection<BaseDict<?,?>> getDictList(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return Optional.ofNullable(dictInMemory.get(name)).map(Map::values).orElse(Collections.emptyList());
    }
}
