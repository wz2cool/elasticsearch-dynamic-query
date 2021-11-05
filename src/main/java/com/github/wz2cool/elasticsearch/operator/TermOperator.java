package com.github.wz2cool.elasticsearch.operator;

public class TermOperator<T> implements IFilterOperator<T> {

    private final T value;

    public TermOperator(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
