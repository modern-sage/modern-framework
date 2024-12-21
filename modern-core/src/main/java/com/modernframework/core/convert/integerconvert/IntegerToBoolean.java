package com.modernframework.core.convert.integerconvert;

public class IntegerToBoolean implements IntegerConverter<Boolean>{

    @Override
    public Boolean convert(Integer source) {
        return source != null && source == 1;
    }
}
