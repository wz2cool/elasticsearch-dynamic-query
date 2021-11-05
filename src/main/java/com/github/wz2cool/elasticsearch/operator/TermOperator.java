package com.github.wz2cool.elasticsearch.operator;

public class TermOperator<T> {

    private final T value;

    public TermOperator(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
