package com.github.wz2cool.elasticsearch.operator;

public class MultiMatchOperator<T> implements IFilterOperator<T> {

    private final String value;

    public MultiMatchOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
