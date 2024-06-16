package com.modernframework.core.convert.multiple;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class StringToNavigableSetConverter extends StringToIterableConverter<NavigableSet> {

    @Override
    protected NavigableSet createMultiValue(int size, Class<?> multiValueType) {
        return new TreeSet();
    }
}
