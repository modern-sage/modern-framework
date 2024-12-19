package com.modernframework.core.convert.listconvert;

import com.modernframework.core.convert.ConvertUtils;
import com.modernframework.core.utils.CollectionUtils;

import java.util.List;

import static com.modernframework.core.constant.StrConstant.COMMA;

/**
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class ListToStringConverter implements ListConverter<String> {

    @Override
    public String convert(List source) {
        if(CollectionUtils.isEmpty(source)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        source.forEach(x -> builder.append(ConvertUtils.convert(x, String.class)).append(COMMA));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
