package com.modernframework.core.convert;


import com.modernframework.core.utils.StringUtils;

import java.net.URI;

public class StringToURIConverter implements StringConverter<URI> {

    @Override
    public URI convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return URI.create(source);
    }
}
